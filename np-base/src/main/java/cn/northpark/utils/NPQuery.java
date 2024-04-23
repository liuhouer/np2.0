
package cn.northpark.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * NorthPark 使用JDBC+hikariCp-pool 来便捷的操作数据
 * @author bruce
 *
 */
@Slf4j
public class NPQuery {

	/** 唯一dateSource，保证全局只有一个数据库连接池 */
	private DataSource dataSource = null;

	private static QueryRunner queryRunner;

	private transient String prop;

	/**
	 * 不允许 new
	 */
	private NPQuery() {
	}

	public NPQuery(String prop) {
		this.prop = prop;
		try {
			dataSource = HikariUtils.getDataSource(prop);
			queryRunner = new QueryRunner(dataSource);
		}catch (Exception e) {
			log.error("获取连接异常 ", e);
		}
	}

	public <T> T query(String sql ,ResultSetHandler<T> resultSetHandler,Object... params){
		T result = null;
		try {
			result = queryRunner.query(sql,resultSetHandler,params);
		}catch (Exception e){
			log.error("",e);
		}
		return result;
	}


	public int update(String sql,Object... params){
		int result = 0;
		try {
			result = queryRunner.update(sql,params);
		}catch (Exception e){
			log.error("",e);
		}
		return result;
	}
	public int insert(String sql,Object... params ){
		int result = 0;
		try {
			result = queryRunner.execute(sql,params);
		}catch (Exception e){
			log.error("",e);
		}
		return result;
	}
	public Map<String,Object> findById(String table, int id){
		String sql = "select * from "+table +" where id = ?";
		return query(sql, new MapHandler(),id);
	}
	public <T> T findById(String table , int id , BeanHandler<T> beanHandler){
		String sql = "select * from "+table +" where id = ?";
		return query(sql, beanHandler,id);
	}
	public List<Map<String,Object>> findByCondition(String table, String condition){
		String sql = "select * from "+table +" where "+ condition;
		return query(sql, new MapListHandler());
	}

	public <T> List<T> findByCondition(String table, String condition , BeanListHandler<T> beanListHandler ){
		String sql = "select * from "+table +" where "+ condition;
		return query(sql, beanListHandler);
	}

	public List<Map<String,Object>> findByCondition(String table, String condition,String sort){
		String sql = "select * from "+table +" where "+ condition + "order by "+ sort;
		return query(sql, new MapListHandler());
	}
	public List<Map<String,Object>> findByCondition(String table, String condition,String sort,String limit){
		String sql = "select * from "+table +" where "+ condition + "order by "+ sort + limit;
		return query(sql, new MapListHandler());
	}


	public void close(){

	}


	public static void main(String[] args) throws SQLException {

		NPQuery nQuery = new NPQuery("local.properties");

		List<Map<String, Object>> lists = nQuery.query("select distinct reqCode from requests", new MapListHandler());

		log.info("lists,------------------>{}",lists);

	}
}

