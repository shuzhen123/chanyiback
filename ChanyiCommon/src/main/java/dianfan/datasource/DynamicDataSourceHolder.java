package dianfan.datasource;

/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 * 
 * @author zhijun
 *
 */
public class DynamicDataSourceHolder {
	private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

	private DynamicDataSourceHolder() {
		//
	}

	public static void putDataSource(DynamicDataSourceGlobal dataSource) {
		holder.set(dataSource);
	}

	public static DynamicDataSourceGlobal getDataSource() {
		return holder.get();
	}

	public static void clearDataSource() {
		holder.remove();
	}

}
