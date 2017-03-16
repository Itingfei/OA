import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestDB {

	/**生成数据库的23张表*/
	@Test
	public void createTable_23(){
		//创建流程引擎
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//配置连接数据库
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://192.168.0.144:3306/da_oa?useUnicode=true&characterEncoding=utf8");
		configuration.setJdbcUsername("liuzhiyi");
		configuration.setJdbcPassword("aaaaaa");
		//数据库中如果没有表，创建表
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//创建流程引擎（核心对象）
		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println("流程引擎对象："+processEngine);
	}

	/**通过activiti.cfg.xml（配置文件）创建流程引擎*/
	@Test
	public void createProcessEnginee(){
		//创建流程引擎
//		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
//		ProcessEngine processEngine = configuration.buildProcessEngine();
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
										.buildProcessEngine();
		System.out.println("流程引擎对象："+processEngine);
	}

	@Test
	public void testJpinyin(){
		String name = "冯骥飞";
		String name1 = "fengjifei";
		String name2 ="杨杰";
		String end = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
		String end1 = PinyinHelper.getShortPinyin(name1);
		String end2 = PinyinHelper.getShortPinyin(name2);
		System.out.println(end);
		System.out.println(end1.substring(0,1));
		System.out.println(end2.substring(0,1));

	}
}
