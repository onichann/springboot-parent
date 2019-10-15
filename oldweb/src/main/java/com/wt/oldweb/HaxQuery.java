package com.wt.oldweb;

import static com.gisinfo.platform.framework.common.util.ObjectUtil.isNotEmptyOrBlank;



import com.gisinfo.platform.framework.core.web.dataaccess.jdbc.BaseQueryCondition;

public class HaxQuery extends BaseQueryCondition{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7256134338927534124L;
	private String featid;
	private String xcdw;
	private String axlx;
	private String qh;
	
	@Override
	protected void buildConditionImpl() {
		// TODO Auto-generated method stub
		if (isNotEmptyOrBlank(featid)) builder().and().sql(" featid in ('"+featid+"')");
		if (isNotEmptyOrBlank(xcdw)) builder().and().stringLikeCause("xcdw", "%"+xcdw+"%"); 
		if (isNotEmptyOrBlank(axlx)) builder().and().stringEqCause("axlx", axlx); 
		if (isNotEmptyOrBlank(qh)) builder().and().stringEqCause("qh", qh); 
	}

	public String getFeatid() {
		return featid;
	}

	public void setFeatid(String featid) {
		this.featid = featid;
	}

	public String getXcdw() {
		return xcdw;
	}

	public void setXcdw(String xcdw) {
		this.xcdw = xcdw;
	}

	public String getAxlx() {
		return axlx;
	}

	public void setAxlx(String axlx) {
		this.axlx = axlx;
	}

	public String getQh() {
		return qh;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}
}
