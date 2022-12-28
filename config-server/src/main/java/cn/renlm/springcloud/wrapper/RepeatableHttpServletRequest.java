package cn.renlm.springcloud.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 请求包装（实现Payload多次读取）
 * 
 * @author Renlm
 *
 */
public class RepeatableHttpServletRequest extends HttpServletRequestWrapper {

	private final byte[] bytes;

	public RepeatableHttpServletRequest(HttpServletRequest request) throws IORuntimeException, IOException {
		super(request);
		bytes = IoUtil.readBytes(request.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream() {
			private int lastIndexRetrieved = -1;
			private ReadListener readListener = null;

			@Override
			public boolean isFinished() {
				return (lastIndexRetrieved == bytes.length - 1);
			}

			@Override
			public boolean isReady() {
				return isFinished();
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				this.readListener = readListener;
				if (!isFinished()) {
					try {
						readListener.onDataAvailable();
					} catch (IOException e) {
						readListener.onError(e);
					}
				} else {
					try {
						readListener.onAllDataRead();
					} catch (IOException e) {
						readListener.onError(e);
					}
				}
			}

			@Override
			public int read() throws IOException {
				int i;
				if (!isFinished()) {
					i = bytes[lastIndexRetrieved + 1];
					lastIndexRetrieved++;
					if (isFinished() && (readListener != null)) {
						try {
							readListener.onAllDataRead();
						} catch (IOException ex) {
							readListener.onError(ex);
							throw ex;
						}
					}
					return i;
				} else {
					return -1;
				}
			}
		};
	}

	@Override
	public BufferedReader getReader() throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		BufferedReader temp = new BufferedReader(new InputStreamReader(is));
		return temp;
	}

}
