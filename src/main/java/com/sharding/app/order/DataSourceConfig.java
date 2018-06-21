package com.sharding.app.order;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 12:14
 **/
@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${db_0.url}")
    private String db_0_url;

    @Value("${db_0.username}")
    private String db_0_username;

    @Value("${db_0.password}")
    private String db_0_password;

    @Value("${db_1.url}")
    private String db_1_url;

    @Value("${db_1.username}")
    private String db_1_username;

    @Value("${db_1.password}")
    private String db_1_password;

    @Bean
    public DataSource getDataSource() {
        return buildDataSource();
    }

    @Bean
    public DefaultKeyGenerator defaultKeyGenerator(){
        return new DefaultKeyGenerator();
    }

    @Bean
    public ObjectMapper myMapper(){
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }


    private DataSource buildDataSource() {
        //设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库ds_0,ds_1到map里
        dataSourceMap.put("ds_0", createDataSource(db_0_url,db_0_username,db_0_password));
        dataSourceMap.put("ds_1", createDataSource(db_1_url,db_1_username,db_1_password));
        //设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "ds_0");

        //设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
        //0和1两个表是真实的表，t_order是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from t_order就能查完0和1表的
        TableRule orderTableRule = TableRule.builder("t_order")
                .autoIncrementColumns("id",CommonSelfIdGenerator.class)
                .actualTables(Arrays.asList("t_order_0", "t_order_1"))
                .dataSourceRule(dataSourceRule)
                .build();

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("create_user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm())).build();

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);

        return dataSource;
    }

    private static DataSource createDataSource(final String url,final String username,final String password) {
        //使用dbcp2连接数据库
        BasicDataSource result=new BasicDataSource();
       // DruidDataSource result = new DruidDataSource();
        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setUrl(url);
        result.setUsername(username);
        result.setPassword(password);
        result.setDefaultAutoCommit(true);
        result.setInitialSize(30);
        result.setMaxTotal(120);
        result.setMaxIdle(120);
        result.setMinIdle(30);
        result.setMaxWaitMillis(10000);
        result.setValidationQuery("SELECT 1");
        result.setValidationQueryTimeout(3);
        result.setTestOnBorrow(true);
        result.setTestWhileIdle(true);
        result.setTimeBetweenEvictionRunsMillis(10000);
        result.setNumTestsPerEvictionRun(10);
        result.setMinEvictableIdleTimeMillis(120000);
        result.setRemoveAbandonedOnBorrow(true);
        result.setRemoveAbandonedTimeout(120);
        result.setPoolPreparedStatements(true);
        return result;
    }
}
