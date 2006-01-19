/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2005  Joerg Mueller, Daniel Polansky, Christian Foltin, Juan Pedro and others.
 *
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Created on 25.04.2005
 */
 
import flash.display.BitmapData;
import flash.geom.Matrix;import flash.filters.DropShadowFilter;
import visorFreeMind.*;

class visorFreeMind.PictureTaker{
	var floor:Floor;
	var browser:Browser;
	var cont:MovieClip;
	var drawer:MovieClip;
	var hider:MovieClip;
	var list:Array=[];
	var dictNames:Object={};
	var listColors:Array=[0xff0000,0xffff00,0x0000ff,0xff00ff];
	var pos:Number=0;
	var format:TextFormat;
	var distributionType:Number=0; //0=folded,1=down,2=spread
	
	public static var  ShotsWidth:Number=180;

	function PictureTaker(browser:Browser){
		this.browser=browser;
		this.floor=browser.floor;
		this.cont=floor.getShotsCont();
		cont.pt=this;
		this.drawer=floor.getCanvas();
		format= new TextFormat();
		format.font = "Arial";
		format.color = 0x666666;
		format.size = 12;
		format.underline = false;
		addOverButton();	

	}

	function addOverButton(){
		var over:MovieClip=cont.createEmptyMovieClip("over",2);
		over.lineStyle(16,0x00ff00,0);
		over.moveTo(10,10);
		over.lineTo(11,10);
		over.inst=this;
		over.onPress=function(){
			over.inst.changeFold();
		}
	}
	
	function changeFold(){
		if(Browser.flashVersion>7){
			distributionType=(distributionType+1)%3;
			relocate();
		}
	}
	
	function relocate(){
		var position:Number=16;
		if(distributionType==0){
			for(var i=0;i<list.length;i++){
				if(Browser.flashVersion>7) list[i]._x=4+i*4;
				else list[i]._x=4;
				list[i]._y=16+i*22;
			}
		}else if(distributionType==2){
			for(var i=0;i<list.length;i++){
				list[i]._x=4;
				list[i]._y=position;
				position=position+list[i]._height+4;
			}
		}else if(distributionType==1){
			var column:Number=0;
			for(var i=0;i<list.length;i++){
				list[i]._x=4+column*(PictureTaker.ShotsWidth+6);
				list[i]._y=position;
				position=position+list[i]._height+4;
				if(position+list[i]._height>Stage.height){
					position=16;
					column++;
				}
			}
		}
	}
	
	function addHider(){
		hider=cont.createEmptyMovieClip("hider",0);
		hider._alpha=50;
		hider.onRollOut=function(){
			this._parent._visible=false;
		}
	}
	
	function show(){
		trace("PT show");
		cont._visible=true;
		cont.onEnterFrame=function(){
			if(this.hitTest(_root._xmouse,_root._ymouse,false)){
				if(this._height>Stage.height){
					var h=Stage.height-60;
					var m=_root._ymouse-30;
					var dest=-(this._height+20-h)*(1+((m)-h)/h);
					if(dest>0) dest=0;
					if(_root._ymouse+60>Stage.height) dest=Stage.height-this._height;
					var dif=Math.abs(Math.abs(this._y)-Math.abs(dest));
					var sig=(this._y-dest)/Math.abs(this._y-dest);
					this._y+=-sig*dif*0.2;
					//trace(this._y +" "+dest+" height:"+this._height+" ymouse:"+_root._ymouse);
				}
				if(this._width>Stage.width){
					var h=Stage.width-60;
					var m=_root._xmouse-30;
					var dest=-(this._width+20-h)*(1+((m)-h)/h);
					if(dest>0) dest=0;
					if(_root._xmouse+60>Stage.width) dest=Stage.width-this._width;
					var dif=Math.abs(Math.abs(this._x)-Math.abs(dest));
					var sig=(this._x-dest)/Math.abs(this._x-dest);
					this._x+=-sig*dif*0.2;
					//trace(this._y +" "+dest+" height:"+this._height+" ymouse:"+_root._ymouse);
				}
			}else{
				this._visible=false;
				this._y=0;
			}
		}
		/*
		hider.clear();
		hider.lineStyle(1,0x00000,100);
		hider.beginFill(0xff0000,100);
		hider.moveTo(0,0);
		hider.lineTo(hider._parent._width,0);
		hider.lineTo(hider._parent._width+20,hider._parent._height+20);
		hider.lineTo(0,hider._parent._height+20);
		hider.lineTo(0,0);
		hider.endFill();
		*/
	}
	
	function hide(){
		cont._visible=false;
		cont.onEnterFrame=undefined;
	}
	
	function genBitMap(){
		var auxMatrix:Matrix = new Matrix();
		var myMatrix:Matrix = new Matrix();
		var bo=drawer.getBounds(drawer); 
		var sx=ShotsWidth/(bo.xMax-bo.xMin);
		var sy=ShotsWidth/(bo.yMax-bo.yMin);
		var auxBitmapData:BitmapData =new BitmapData(ShotsWidth, ShotsWidth*sx/sy, false, 0xffffffff);
		var myBitmapData:BitmapData =new BitmapData(ShotsWidth, ShotsWidth*sx/sy, false, 0xffffffff);
		sy=sx;
		auxMatrix.translate(-bo.xMin,-bo.yMin);
		auxMatrix.scale(sx,sy);
		auxBitmapData.draw(drawer,auxMatrix,null,null,null,true);
		//this second draw is very important for obtaining a smooth image
		myBitmapData.draw(auxBitmapData,myMatrix,null,null,null,true);
		auxBitmapData.dispose();
		return myBitmapData;
	}


	function takeShot(name:String){
		browser.deleteHidden();
		var movie:MovieClip=cont.createEmptyMovieClip("etiqueta"+pos,list.length+20);
		movie.pt=this;
		var textoCont:MovieClip=movie.createEmptyMovieClip("textoCont",0);
		var t:TextField=addName(textoCont,name);
		addShow(textoCont);
		if(Browser.flashVersion>7){
			var cont:MovieClip=movie.createEmptyMovieClip("shot",2);
			cont.attachBitmap(genBitMap(),cont.getNextHighestDepth(),"auto", true);
			cont._y=t._height;
			createDropShadowRectangle(movie);
		}

		if(dictNames[name]!=undefined){
			var pos:Number=dictNames[name];
			movie.swapDepths(list[pos]);
			list[pos].removeMovieClip();
			list[pos]=movie;
		}else{
			list.push(movie);
			dictNames[name]=list.length-1;
		}
		
		relocate();
		pos++;
	}
	
	function addGo(select:MovieClip){
		select.onRollOver=function(){
			this.pt.addArrow(this,0xffee99);
		}	
		select.onRollOut=function(){
			this.pt.addArrow(this,0xffdd88);
		}
	}
	
	function addArrow(select:MovieClip,color:Number){
		select.clear();
		select.lineStyle(16,color,100);
		select.moveTo(9,9);
		select.lineTo(10,9);
		select.lineStyle(3,0xFFFFFF,100);
		select.moveTo(10,4);
		select.lineTo(15,9);
		select.lineTo(10,14);
		select.moveTo(4,9);
		select.lineTo(14,9);
	}
	
	function despDown(n:Number){
		for(var i=0;i<list.length;i++){
			list[i]._y=list[i]._y+n*15;
		}
	}
	
	function despUp(n:Number){
		for(var i=0;i<list.length;i++){
			list[i]._y=list[i]._y-n*15;
		}
	}
	
	function removeShot(){
		list.pop().removeMovieClip();
	}
	
	function addName(m:MovieClip,name:String){
		m.createTextField("texto",m.getNextHighestDepth(),0,0,0,0);
		var t:TextField=m.texto;
		t.text=name;
		t.autoSize=true;
		t.textColor=0x000000;
		t.background=true;
		t.backgroundColor=0xFFDD88;
		t.selectable=false;
		t.setTextFormat(format);
		return t;
	}

	function addShow(t:MovieClip){
		t.time=getTimer();
	    t.onPress=function(){
	    	this._parent.startDrag();
	    	this._parent.oDepth=this._parent.getDepth();
	    	this._parent.swapDepths(100);
	    }
	    t.onRelease=function(){
	    	var time=getTimer();
    		this._parent.stopDrag();
    		this._parent.swapDepths(this._parent.oDepth);
	    	if(time-this.t<280) 
	    	   this._parent.pt.browser.historyManager.historyJump(this.texto.text);
	    	else{
	    		this.t=time;
	    	}
	    }
	    t.onReleaseOut=function(){
	    	this._parent.swapDepths(this._parent.oDepth);
	    }

		t.onRollOver=function(){
			trace("highlighting");
			//this.texto.backgroundColor=0xffee99;
		}	
		t.onRollOut=function(){
			trace("highlighting off");
			//this.texto.backgroundColor=0xFFDD88;
		}	
	}
	
	function createDropShadowRectangle(art:MovieClip) {
		var filter:DropShadowFilter = new DropShadowFilter(4, 45, 0x000000, .6, 6, 6, 1, 3, false, false, false);
		var filterArray:Array = new Array();
		filterArray.push(filter);
		art.filters = filterArray;
		art.filter=filter;
	}
}