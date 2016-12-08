package nz.co.thescene.client.traverson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ch.qos.logback.classic.Logger;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	private Logger log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	//private Log log = LogFactory.getLog(LoggingRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		ClientHttpResponse response = execution.execute(request, body);

		if (log.isDebugEnabled()) { 
			log(request, body, response);
		}

		return response;
	}

	private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
		StringBuilder logOutput = new StringBuilder();
		String lineSeparator = "line.separator";
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append("*************************** REQUEST ****************************");
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append(prettyPrintHeaders(request.getHeaders()));
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append(request.getMethod() + " " + request.getURI());
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append(new String(body));
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append("*************************** RESPONSE ***************************");
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append(response.getStatusCode() + " " + response.getStatusText());
		logOutput.append(System.getProperty(lineSeparator));
		logOutput.append(prettyPrintHeaders(response.getHeaders()));
		logOutput.append(System.getProperty(lineSeparator));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		IOUtils.copy(response.getBody(), output);
		String uglyJSONString = new String(output.toByteArray());
		String prettyJsonString = formatJson(uglyJSONString);
		logOutput.append(prettyJsonString);
		log.debug(logOutput.toString());
	}

	private String formatJson(String uglyJSONString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}

	private String prettyPrintHeaders(HttpHeaders headers) {
		StringBuilder stringBuilder = new StringBuilder();
		Set<String> headerNames = headers.keySet();
		for (String headerName : headerNames) {
			List<String> headerValues = headers.get(headerName);
			for (String headerValue : headerValues) {
				stringBuilder.append(headerName);
				stringBuilder.append(" : ");
				stringBuilder.append(headerValue);
				stringBuilder.append(System.getProperty("line.separator"));
			}
		}
		return stringBuilder.toString();
	}
}
