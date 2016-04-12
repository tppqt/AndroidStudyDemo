package xsf.okhttp.bean.weather;

import java.util.List;

public class Result {

	public String currentCity;
	public String pm25;
	public List<IndexItem> index;
	public List<WeatherData> weather_data;


	@Override
	public String toString() {
		return "Result [currentCity=" + currentCity + ", index=" + index
				+ ", pm25=" + pm25 + ", weather_data=" + weather_data + "]";
	}

	/*public List<IndexItem> getIndex() {
		return index;
	}

	public void setIndex(List<IndexItem> index) {
		this.index = index;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public List<WeatherData> getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(List<WeatherData> weatherData) {
		weather_data = weatherData;
	}*/

}
