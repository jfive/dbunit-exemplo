package br.com.jfive.dbunit.test;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: rodrigoalmeida
 * Date: 07/09/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class TesteExemploMysql {

    private IDatabaseTester databaseTester;

    @Before
    public void init() throws Exception {
        databaseTester = new JdbcDatabaseTester(com.mysql.jdbc.Driver.class.getName(), "jdbc:mysql://127.0.0.1/test", "root", "9330923a");


        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset/Pessoa.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();
    }



    @Test
    public void firstTestMysql() throws SQLException, Exception {
        PreparedStatement statement = databaseTester.getConnection().getConnection().prepareStatement("SELECT * FROM pessoa");
        ResultSet rs = statement.executeQuery();
        rs.next();
        assertEquals(1L, rs.getLong("id"));
        assertEquals("Rodrigo Almeida", rs.getString("name"));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("27/03/1983", formato.format(rs.getDate("dataNascimento")));

    }

    @After
    public void cleanUp() throws Exception {
        databaseTester.onTearDown();
    }

}
