<h1>
    <span>WebRTC two-way Audio/Video/Data Intercom & Recorder</span>
</h1>
<h3 style="color:red" >
    <span>WARNING! Some browsers do not allow to access local media on insecure origins.</span>
    <span>Consider switching the UV4L Streaming Server to secure HTTPS instead.</span>
</h3>
<div id="container">
    <div class="overlayWrapper">
        <video id="remote-video" autoplay="" width="640" height="480">
            Your browser does not support the video tag.
        </video>
        <p class="overlay">remote</p>
    </div>
    <div class="overlayWrapper">
        <video id="local-video" autoplay="" width="320" height="240">
            Your browser does not support the video tag.
        </video>
        <p class="overlay">local</p>
    </div>
</div>
<div id="controls">
    <button type=button id="pause" onclick="pause();" title="pause or resume local player">Pause/Resume</button>
    <button type=button id="mute" onclick="mute();" title="mute or unmute remote audio source">Mute/Unmute</button>
    <button type=button id="fullscreen" onclick="fullscreen();">Fullscreen</button>
    <button type=button id="record" onclick="start_stop_record();" title="start or stop recording audio/video">Start Recording</button>
</div>
<fieldset>
    <legend><b>Remote peer options</b></legend>
    <div>
        <span>Video:</span>
        <label><input type="checkbox" onclick="remote_hw_vcodec_selection();"  id="remote_hw_vcodec" name="remote_hw_vcodec" value="remote_hw_vcodec" title="try to force the use of the hardware codec for both encoding and decoding if enabled and supported">force use of hardware codec for</label>
        <select id="remote_vformat" name="remote_vformat" onclick="remote_hw_vcodec_format_selection();" title="available resolutions and frame rates at the min., max. and start configured bitrates for adaptive streaming which will be scaled from the base 720p 30fps">
            <option value="10">320x240 30 fps</option>
            <option value="20">352x288 30 fps</option>
            <option value="30">640x480 30 fps</option>
            <option value="35">800x480 30 fps</option>
            <option value="40">960x720 30 fps</option>
            <option value="50">1024x768 30 fps</option>
            <option value="55">1280x720 15 fps</option>
            <option value="60" selected="selected">1280x720 30 fps, kbps min.256 max.4000 start800</option>
            <option value="65">1280x768 15 fps</option>
            <option value="70">1280x768 30 fps</option>
            <option value="80">1280x960 30 fps</option>
            <option value="90">1600x768 30 fps</option>
            <option value="95">1640x1232 15 fps</option>
            <option value="97">1640x1232 30 fps</option>
            <option value="100">1920x1080 15 fps</option>
            <option value="105">1920x1080 30 fps</option>
        </select>
        <p id="note1_"><small>NOTE: if your browser does not support the hardware codec yet, try Firefox with the codec plugin enabled or a recent version of Chrome.</small></p>
    </div>
</fieldset>
<div>
    <details id="record-detail">
        <summary><b>Recorded Audio/Video stream</b></summary>
        <div>
            <div class="overlayWrapper">
                <video id="recorded-video" controls>
                    Your browser does not support the video tag.
                </video>
                <p class="overlay">recorded</p>
            </div>
        </div>
        <div>
            <p><small>NOTE: some old Chrome version may generate corrupted video if the audio track is not present as well (use Firefox in this case)</small></p>
            <button type=button id="discard" onclick="discard_recording();" title="discard recorded audio/video">Discard</button>
            <button type=button id="download" onclick="download();" title="save recorded audio/video">Save as</button>
        </div>
    </details>
</div><br>
<fieldset>
    <legend><b>Cast local Audio/Video sources to remote peer</b></legend>
    <div>
        <span>Audio:</span>
        <label><input type="checkbox" onclick="singleselection('audio_cast', 'cast_mic');" id="cast_mic" name="audio_cast" value="microphone">microphone/other input</label>
        <label><input type="checkbox" id="echo_cancellation" name="audio_processing" title="disable any audio processing when casting music" checked>echo cancellation</label>
        <!--label><input type="checkbox" onclick="singleselection('audio_cast', 'cast_tab');" id="cast_tab" name="audio_cast" value="system">tab</label-->
    </div>
    <div>
        <span>Video:</span>
        <label><input type="checkbox" onclick="singleselection('video_cast', 'cast_camera');" id="cast_camera" name="video_cast" value="camera">camera</label>
        <label><input type="checkbox" onclick="singleselection('video_cast', 'cast_screen');" id="cast_screen" name="video_cast" value="screen">screen</label>
        <label><input type="checkbox" onclick="singleselection('video_cast', 'cast_window');" id="cast_window" name="video_cast" value="screen">window</label>
        <label><input type="checkbox" onclick="singleselection('video_cast', 'cast_application');" id="cast_application" name="video_cast" value="application">application</label>
        <p id="note1"><small>NOTE: except camera, screen, window or application casting is supported from Firefox 44 on and over HTTPS only.</small></p>
        <p id="note2"><small>NOTE: except camera, to enable screen, window or application casting open <i>about:config</i> URL
                and set <i>media.getusermedia.screensharing.enabled</i> to <i>true</i>
                and permanently add the current domain to the list in <i>media.getusermedia.screensharing.allowed_domains.</i></small>
        </p>
        <p id="note3"><small>NOTE: if you want to cast music, for better audio quality disable <i>echo-cancellation.</i></small></p>
        <p id="note4"><small>NOTE: if you want to cast music, for better audio quality disable <i>echo-cancellation</i>,
                and <i>aec</i>, <i>noise-suppression</i>, <i>agc</i> in the browser configuration <i>(about:config).</i></small>
        </p>
    </div>
</fieldset>
<div>
    <details>
        <summary><b>Data Channels</b></summary>
        <fieldset id="datachannels" disabled>
            <span>message: </span><input type="text" id="datamessage" value="" title="message to send to the remote peer"/>
            <button id="datasend" onclick="send_message();">Send</button>
            <span>received: </span><input type="text" readonly="readonly" id="datareceived" size="40" title="data received from the remote peer"/><br>
            <label><input type="checkbox" onclick="orientationsend_selection();" id="orientationsend" name="orientationsend" title="send device orientation angles when they change">send device orientation angles alpha, beta, gamma</label>
            <label><input type="checkbox" onclick="keypresssend_selection();" id="keypresssend" name="keypresssend" title="send keyboard events. Assume US layout. For users with virtual keyboard: put the focus on the 'message' input text item.">send key codes (US layout)</label>
            <label><input type="checkbox" onclick="alert('not implemented yet');" id="mousesend" name="mousesend" title="send mouse events">send mouse events</label>
        </fieldset>
        <!--fieldset id="localdatachannels">
            <button id="datacreate" onclick="create_localdatachannel();">Create</button>
            <button id="dataclose" onclick="close_localdatachannel();">Close</button>
        </fieldset-->
    </details>
</div><br>
<div id="commands">
    <details open>
        <summary><b>Advanced options</b></summary>
        <fieldset>
            <span>Remote Peer/Signalling Server Address: </span><input required type="text" id="signalling_server" value="193.48.125.196:8080" title="<host>:<port>, default address is autodetected"/><br>
            <span>Optional ICE Servers (STUN/TURN): </span><input type="text" id="ice_servers" value="" title="array of RTCIceServer objects as valid JSON string"/>
        </fieldset>
    </details>
    <button id="start" style="background-color: green; color: white" onclick="start();">Call!</button>
    <button disabled id="stop" style="background-color: red; color: white" onclick="stop();">Hang up</button>
</div><br>
<a target="_top" href="/">home</a>&nbsp;<a href="/panel" target="_blank" title="change the image settings on-the-fly">control panel</a>
