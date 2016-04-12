package xsf.okhttp.bean.weather;

public class WeatherData {

	public String date;
	public String dayPictureUrl;
	public String weather;
	public String temperature;
	public String wind;

	@Override
	public String toString() {
		return "WeatherData [date=" + date + ", dayPictureUrl=" + dayPictureUrl
				+ ", temperature=" + temperature + ", weather=" + weather
				+ ", wind=" + wind + "]";
	}

	/*public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDayPictureUrl() {
		return dayPictureUrl;
	}

	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}*/

}