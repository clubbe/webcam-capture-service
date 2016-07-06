package com.zailab.webcam;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.zailab.WebcamSettings;

@Component
public class MotionActivatedWebcamController implements WebcamMotionListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final WebcamSettings settings;
	private final ScheduledThreadPoolExecutor executor;
	private final Webcam webcam;

	private WebcamMotionDetector webcamMotionDetector;
	private CustomWebcamStreamer webcamStreamer;
	private ScheduledFuture<?> schedule;

	@Autowired
	public MotionActivatedWebcamController(WebcamSettings settings, ScheduledThreadPoolExecutor executor, Webcam webcam) {
		super();
		this.settings = settings;
		this.executor = executor;
		this.webcam = webcam;
	}

	@PostConstruct
	public void start() {

		webcamMotionDetector = new WebcamMotionDetector(webcam);
		webcamMotionDetector.addMotionListener(this);
		webcamMotionDetector.start();

		webcamStreamer = new CustomWebcamStreamer(settings.getSocketport(), webcam, settings.getFps(), false);
		webcamStreamer.start();
	}

	@PreDestroy
	public void stop() {

		cancelScheduledPause();

		webcamMotionDetector.stop();
		webcamStreamer.stop();
		webcam.close();
	}

	public void motionDetected(WebcamMotionEvent webcamMotionEvent) {

		if (schedule == null) {

			webcamStreamer.play();
		} else {

			cancelScheduledPause();
		}

		schedulePause();
	}

	private void cancelScheduledPause() {

		schedule.cancel(true);
	}

	private void schedulePause() {

		Runnable command = new Runnable() {

			public void run() {

				webcamStreamer.pause();
				schedule = null;
			}
		};
		long delay = 800;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		schedule = executor.schedule(command, delay, unit);
	}
}
