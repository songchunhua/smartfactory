package com.mesplus.MBI.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.mesplus.util.ResultSetUtils;

public class TbldatNt extends StoredProcedure {
	public static final String FAC_ID_PARAM = "fac_id";
	public static final String TBL_CODE_PARAM = "tbl_code";
	public static final String LANG_FLAG_PARAM = "lang_flag";
	public static final String PARAMS_PARAM = "params";
	public static final String CUR_REFER_PARAM = "cur.refer";

	private static final String SPROC_NAME = "P_AGCMTBLDAT_NT";
	
	public TbldatNt(DataSource dataSource) throws SQLException {
		super(dataSource, SPROC_NAME);

		declareParameter(new SqlParameter(FAC_ID_PARAM, Types.VARCHAR));
		declareParameter(new SqlParameter(TBL_CODE_PARAM, Types.VARCHAR));
		declareParameter(new SqlParameter(LANG_FLAG_PARAM, Types.VARCHAR));
		declareParameter(new SqlParameter(PARAMS_PARAM, Types.VARCHAR));
		
		declareParameter(new SqlOutParameter(CUR_REFER_PARAM, OracleTypes.CURSOR, new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				return ResultSetUtils.convertResultSetToMap(rs);
			}
		}));

		compile();
	}

	public Map<String, Object> execute(String fac_id, String tbl_code, String lang_flag, String params) {
		Map<String, Object> inputs = new HashMap<String, Object>();
		inputs.put(FAC_ID_PARAM, fac_id);
		inputs.put(TBL_CODE_PARAM, tbl_code);
		inputs.put(LANG_FLAG_PARAM, lang_flag);
		inputs.put(PARAMS_PARAM, params);

		return super.execute(inputs);
	}
}
