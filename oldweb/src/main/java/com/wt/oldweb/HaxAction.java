package com.wt.oldweb;

import static com.gisinfo.platform.framework.core.web.struts2.action.ExecuteResult.currentResult;
import static com.gisinfo.platform.framework.core.web.struts2.action.ExecuterManager.daoExecuter;
import static com.gisinfo.platform.framework.core.web.struts2.action.ExecuterManager.parameterExecuter;
import static com.gisinfo.platform.framework.core.web.struts2.action.ExecuterManager.resultExecuter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gisinfo.platform.framework.common.util.ObjectUtil;
import com.gisinfo.platform.framework.core.anno.AutoDetect;
import com.gisinfo.platform.framework.core.anno.AutoJson;
import com.gisinfo.platform.framework.core.anno.AutoTransactional;
import com.gisinfo.platform.framework.core.anno.AutoView;
import com.gisinfo.platform.framework.core.web.data.DataModelManager;
import com.gisinfo.platform.framework.core.web.data.PageDataGridDataModelRecordBuilderCreator;
import com.gisinfo.platform.framework.core.web.data.grid.PageDataGridDataModel;
import com.gisinfo.platform.framework.core.web.dataaccess.IJdbcDao;
import com.gisinfo.platform.framework.core.web.dataaccess.sql.IJdbcQueryBuilder;
import com.gisinfo.platform.framework.core.web.dataaccess.sql.JdbcSQLUtil;
import com.gisinfo.web.app.commonUtil.DictManager;
import com.gisinfo.webService.Interface.Impl.Utils;

@AutoDetect
public class HaxAction {
	
	private HaxQuery listQuery;
	private static final String tableName="ZTSJ_GLAX";
	
	@AutoView
	public void list(){
		
	}
	
	@AutoJson
    public void listPageData() {
        resultExecuter().json((String) currentResult().toJson());
    }

    @SuppressWarnings("unchecked")
	@AutoTransactional
    public void listPageData$() {
        parameterExecuter().assertIntParam("page", "参数[page]错误!");
        parameterExecuter().assertIntParam("rows", "参数[rows]错误!");
        int page = parameterExecuter().intParam("page");
        int rows = parameterExecuter().intParam("rows");
        final String sort=parameterExecuter().stringParam("sort");
	    final String order=parameterExecuter().stringParam("order");
        IJdbcDao dao = daoExecuter().jdbcDao();
        IJdbcQueryBuilder countBuilder = JdbcSQLUtil.createQueryBuilder("select count(*) c from "+tableName+" WHERE 1=1");
        if(listQuery!=null){
        	countBuilder.and().sql(listQuery.createSql()).parameter(listQuery.createParameters());
        }
		PageDataGridDataModel dm = DataModelManager.createPageDataGridDataModel(dao, countBuilder,
			new PageDataGridDataModelRecordBuilderCreator() {
				public IJdbcQueryBuilder createRecordBuilder(int startIndex, int endIndex) {
					final IJdbcQueryBuilder builder = JdbcSQLUtil.createQueryBuilder("select * from(");
			        builder.sql("\n");
			        builder.sqlAndParameter("select t.*,rownum r_ from "+tableName+" t WHERE 1=1 ");
			        if(listQuery!=null){
			        	builder.and().sql(listQuery.createSql()).parameter(listQuery.createParameters());
			        }
			        builder.sql("\n");
			        builder.sqlAndParameter(") t where t.r_>? and t.r_<=? ", startIndex, endIndex);
			        if(ObjectUtil.isNotEmptyOrBlank(sort)&&ObjectUtil.isNotEmptyOrBlank(order)){
			        	builder.sql(" order by "+sort+" "+order);
			        }
			       return builder;
				};
		}, rows, page);
		DictManager.dataRow(tableName, dm.currentRecords(), null);
        currentResult().setTargetResult(dm);
    }
    
	@AutoJson
    public void getCombo(){
    	currentResult().setTargetResult(Utils.getCombo(tableName));
    	resultExecuter().json((String) currentResult().toJson());
    }
	public HaxQuery getListQuery() {
		return listQuery;
	}
	public void setListQuery(HaxQuery listQuery) {
		this.listQuery = listQuery;
	}

}
