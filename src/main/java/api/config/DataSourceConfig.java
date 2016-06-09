package api.config;

import api.dao.IndirizzoRepository;
import api.dao.ItemsRepository;
import api.dao.PersonaRepository;
import api.model.Persona;
import com.zaxxer.hikari.HikariDataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.skife.jdbi.v2.spring.DBIFactoryBean;
import org.skife.jdbi.v2.tweak.SQLLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Created by matthew on 16.05.16.
 */
@Configuration
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.username:corso}")
    private String username;

    @Value("${spring.datasource.password:corso-2016}")
    private String password;

    @Value("${spring.datasource.url:jdbc:postgresql://ekr-pg-corso:5432/corso-4}")
    private String dataSourceUrl;

    @Value("${spring.datasource.dataSourceClassName:org.postgresql.Driver}")
    private String dataSourceClassName;

    //QUESTA METODO SOVRASCRIVE QUELLO CHE C'E' GIA'
    @Bean
    public javax.sql.DataSource dataSource() {

        // HIKARI GESTISCE IL NUMERO DI CONNESSIONI........... SIMILE A THREAD POOL

        //hikari garantisce un numero di connessioni

        HikariDataSource cpdb = new HikariDataSource();
        cpdb.setMaximumPoolSize(10);
        cpdb.setPoolName("corso-cp");
        String dsUrl = dataSourceUrl;
        if (dataSourceClassName.toLowerCase().contains("postgres")) {
            if (!dsUrl.contains("stringtype=unspecified")) {
                dsUrl += "?stringtype=unspecified";
            }
        } else if (dsUrl.contains(":sap:")) {
            cpdb.setConnectionTestQuery("SELECT 1 FROM DUMMY");
        }
        cpdb.setJdbcUrl(dsUrl);
        cpdb.setDriverClassName(dataSourceClassName);
        cpdb.setUsername(username);
        cpdb.setPassword(password);
        try {
            cpdb.getConnection().isValid(1000);
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return cpdb;
    }

    @Bean
    public DBIFactoryBean dbiFactoryBean() {
        DBIFactoryBean bean = new DBIFactoryBean();
        bean.setDataSource(dataSource());
        return bean;
    }

    @Bean
    public SQLLog sqlLog() {
        SQLLog sqlLog = new SLF4JLog();
        return sqlLog;
    }

    @Bean(destroyMethod = "")
    public DBI dbi() throws Exception {
        DBI dbi = (DBI) dbiFactoryBean().getObject();
        dbi.setSQLLog(sqlLog());
        return dbi;
    }

    // INDICA A DBI CHE QUANDO SERVE CREA UN'INSTANZA DELL'AUTO REPOSITORY
    @Bean
    public ItemsRepository itemsRepository() throws Exception {
        //crea il nostro repository indica che dbi quando è richiesto crea un istanza del repository
        return dbi().onDemand(ItemsRepository.class);
    }
    @Bean
    public PersonaRepository personaRepository() throws Exception {
        //crea il nostro repository indica che dbi quando è richiesto crea un istanza del repository
        return dbi().onDemand(PersonaRepository.class);
    }
    @Bean
    public IndirizzoRepository indirizzoRepository() throws Exception {

        return dbi().onDemand(IndirizzoRepository.class);
    }
}
