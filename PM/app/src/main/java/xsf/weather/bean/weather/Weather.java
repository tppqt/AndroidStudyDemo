package xsf.weather.bean.weather;

import java.util.List;

/**
 * 说明：天气信息model
 */

public class Weather {
	public int error;
	public String status;
	public String date;
	public List<Result> results;

	@Override
	public String toString() {
		return "Weather [date=" + date + ", error=" + error + ", status="
				+ status + ", results=" + results + "]";
	}

	/*public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}*/

}
