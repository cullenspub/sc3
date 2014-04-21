#Sample file for learning
s.boot;

(
	SynthDef("test", { arg out, freq, xFade;
		XOut.ar(out, xFade, SinOsc.ar(freq))
	}).store
	);

SynthDescLib.global.browse; // browse the properties of SynthDescs

w = Window.new(\panel,Rect(500,200,400,600));
z = w.bounds();
z.width;
size;
Window.screenBounds();
k = Knob.new(w,Rect(10,5,30,30));
s.boot;

floor(25/8);
3%3 == 0;

(
var addKnobs;
w = Window.new(\panel,Rect(500,200,400,600));
addKnobs = {arg window, knobCount = 2, knobSize = 35, hMargin = 10, vMargin = 10;
	var vOffset, hOffset, row = -1, nbrOfKnobsWide = 1 ;
	nbrOfKnobsWide = floor(window.bounds.width / (knobSize + hMargin));
	knobCount.do(
		{
			arg index;
			if(index % nbrOfKnobsWide == 0,{row = row + 1;});
			vOffset = (knobSize * row) + (vMargin * row);
			hOffset = (knobSize * (index % nbrOfKnobsWide)) + (hMargin * (index % nbrOfKnobsWide));
			postln((hMargin + hOffset) + " , " + vMargin + " , " + knobSize + " , " + knobSize + " , " + nbrOfKnobsWide);

			Knob.new(window,Rect((hMargin + hOffset),(vMargin + vOffset),knobSize,knobSize));
			postln("KnobNumber: " + index + " WindowWidth: " + window.bounds.width);

		};
	);
};
addKnobs.value(w,50);
w.front;
)


















(
var syn;
syn = SynthDef.new(name:"example3",
	ugenGraphFunc:{
		arg out = 0, freq = 440, amp = 0.2, dur = 0.1;
		var wave, env_gen, env;
		env = Env.triangle(dur,amp);
		env_gen = EnvGen.kr(env,doneAction:2);
		wave = SinOsc.ar(freq:freq,mul:env_gen);
		Out.ar(out,wave);
});
syn.add;

SynthDescLib.global[\example3].makeWindow;

)
Synth.new(\example3,[\dur,3.0,\out,0]);

(
var func,rout;
func = { arg ratio_arr = [1/1], baseFreq = 440, detune = 10;
	var pitch;
	Routine.new({
		ratio_arr.do( {
			arg value,index;
			pitch = (value * baseFreq) + detune;
			Synth.new(\example3,[\freq,pitch,\dur,1]);
			1.wait;
		});
	});
};
rout = func.value([1/1,3/2,4/3,9/8,16/9,5/4,8/5,2/1].scramble);
rout.play;
)


SynthDesc.global[\example3].makeWindow;

d = SynthDef(\mdDemo, {
	|out, freq, cutoff, volume, gate = 1|
	var sig = LPF.ar(Saw.ar(freq, volume), cutoff), env = EnvGen.kr(Env.adsr, gate, doneAction: 2);
	    Out.ar(out, (sig * env) ! 2)}).add;

SynthDescLib.global[\mdDemo].makeWindow;
