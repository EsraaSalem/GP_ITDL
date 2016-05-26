package dataEntities;

import java.sql.Timestamp;

public class RerankingLastUpdate {

	private String recordID_DB;
	private Timestamp lastDateAlgorithmRun;
	
	
	public RerankingLastUpdate() {
		super();
	}
	public RerankingLastUpdate(String recordID_DB, Timestamp lastDateAlgorithmRun) {
		super();
		this.recordID_DB = recordID_DB;
		this.lastDateAlgorithmRun = lastDateAlgorithmRun;
	}
	public String getRecordID_DB() {
		return recordID_DB;
	}
	public void setRecordID_DB(String recordID_DB) {
		this.recordID_DB = recordID_DB;
	}
	public Timestamp getLastDateAlgorithmRun() {
		return lastDateAlgorithmRun;
	}
	public void setLastDateAlgorithmRun(Timestamp lastDateAlgorithmRun) {
		this.lastDateAlgorithmRun = lastDateAlgorithmRun;
	}
	
	
}
