# Webcam Capture Service

## Reference

See [sarxos' webcam-capture](https://github.com/sarxos/webcam-capture)

> The concepts in that library was used to create this motion activated webcam scanner service

## Usage

Build and run the jar:

    $ mvn package
    $ java -jar target\webcam-capture-service-0.0.1-SNAPSHOT.jar
    
Open [the local stream] (http://localhost:8321) in your browser

## Integration

The server accepts connections on port 8321 by default, to show the stream in a html page use the following code:

	<canvas id="webcam-capture" width="1920" height="1080"></canvas>
	
	<script>
	
		var canvas = document.getElementById('webcam-capture');
		var context = canvas.getContext('2d');
		var imageObj = new Image();

		imageObj.onload = function() {
			context.drawImage(imageObj, 0, 0);
		};
		
		var delay = 1000 / 25; // 25 fps

		var capture = function() {

			imageObj.src = 'http://localhost:8321';
			setTimeout(capture, delay);
		}

		setTimeout(capture, delay);
	</script>

## Configuration

The default settings are:

	webcam.name=foo
	webcam.width=1920
	webcam.height=1080
	webcam.fps=25
	webcam.socketport=8321
	
The settings can be changed by adding -Dwebcam.<property>=<newvalue> to the java command, for example to use a custom webcam name try:

	java -Dwebcam.name="Your webcam's name" -jar target\webcam-capture-service-0.0.1-SNAPSHOT.jar
