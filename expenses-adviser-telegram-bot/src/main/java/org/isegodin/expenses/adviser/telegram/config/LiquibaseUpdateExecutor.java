package org.isegodin.expenses.adviser.telegram.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.BeanClassLoaderAware;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author i.segodin
 */
public class LiquibaseUpdateExecutor implements BeanClassLoaderAware {

    protected String changeLog = "main-changelog.xml";

    protected DataSource dataSource;

    protected Map<String, String> changeLogParameters;

    protected ClassLoader classLoader;

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

    @PostConstruct
    public void runLiquibaseUpdate() throws LiquibaseException, SQLException {
        Connection c = dataSource.getConnection();
        Liquibase liquibase = null;
        try {
            ClassLoader classLoader = this.classLoader;
            liquibase = new Liquibase(changeLog, new ClassLoaderResourceAccessor(classLoader), DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c)));
            if (changeLogParameters != null) {
                for (Map.Entry<String, String> entry : changeLogParameters.entrySet()) {
                    liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
                }
            }
            liquibase.update("");
        } finally {
            if (liquibase != null) {
                Database database = liquibase.getDatabase();
                if (database != null) {
                    database.close();
                }
            }
        }
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setChangeLogParameters(Map<String, String> changeLogParameters) {
        this.changeLogParameters = changeLogParameters;
    }

}