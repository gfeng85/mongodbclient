import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ConnMongoDB {
	
	/**
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {

		// TODO Auto-generated method stub
		// 连接本地数据库
		Mongo m = new Mongo();
		// 创建名为new_test_db的数据库
		DB db = m.getDB("gaofeng1");
		// 获取db中的集合
		Set<String> cols = db.getCollectionNames();
		// 打印出db中的集合，这里应当为null
		for (String s : cols) {
			System.out.println(s);
		}
		// 创建一个集合
		DBCollection collection = db.getCollection("info");
		// 初始化一个基本DB对象，最终插入数据库的就是这个DB对象
		BasicDBObject obj = new BasicDBObject();
		// 放入几个键值对
		obj.put("key1", "val1");
		obj.put("key2", "val2");
		obj.put("key3", "val3");
		//插入对象
		collection.insert(obj);
		//查看一条记录，findOne()=find().limit(1);
		DBObject dbobj=collection.findOne();
		//打印出刚才插入的数据
		System.out.println(dbobj);
		//现在我们来插入9条{ranking:i}的数据
		for(int i=0;i<9;i++){
			collection.insert(new BasicDBObject().append("ranking", i));
		}
		//打印集合中的数据总数，这里应当输出10
		System.out.println(collection.getCount());
		//下面我们来遍历集合，find()方法返回的是一个游标(cursor)，这里的概念和关系数据库很相似
		DBCursor cursor=collection.find();
		//然后我们使用这个游标来遍历集合
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		//下面来看一些略复杂一点的查询技巧，第一个，简单的条件查询，查询ranking为1的记录
		BasicDBObject query=new BasicDBObject();
		query.put("ranking", 1);
		cursor=collection.find(query);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		//下面是更复杂的条件查询，查询ranking大于5小于9的记录
		query=new BasicDBObject();
		query.put("ranking", new BasicDBObject("$gt", 5).append("$lt", 9));
		cursor=collection.find(query);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		//最后删除我们的测试数据库
		m.dropDatabase("gaofeng1");
	
	}

}
