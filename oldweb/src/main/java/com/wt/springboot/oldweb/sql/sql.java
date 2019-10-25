package com.wt.springboot.oldweb.sql;
public class sql{
        String MpbzTableName="sss";
        String sql="merge into "+MpbzTableName+" m using(select ? as id from dual) t on (m.ssbh=t.id) when matched then update set m.wxmpbz_guid=?,ssxzqh=?,cqr=?,xmpdz=?," +
        "sbr=?,lxdh=?,zb=?,sbsj=?,mpzh=?,bz=?,ympdz=? when not matched then insert(m.wxmpbz_guid,m.ssbh,m.ssxzqh,m.cqr,m.xmpdz,m.sbr,m.lxdh,m.zb,m.sbsj,m.mpzh,m.bz" +
        ",m.ympdz) values(?,?,?,?,?,?,?,?,?,?,?,?)";

}

