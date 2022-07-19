/* jQuery Progress Bar Plugin - v1.0.0
 * Copyright (c) 2015 Zeyu Feng; Licensed MIT
 * https://github.com/clarkfbar/jquery.progress
 * */

$.fn.extend({
  Progress: function(options){
    var settings = {
	  parentWidth: 1000, //scriptlet
	  childSizePercent: 1.0, //100%
      height: 20,
      percent: 0,
      backgroundColor: '#555',
      barColor: '#d9534f', //appel d'une fonction qui retourne un #111111 dans cette fonction mettre des case en fonnction des %
      fontColor: '#fff',
      radius: 4,
      fontSize: 12,
      increaseTime: 1000.00/60.00,
      increaseSpeed: 1,
      animate: true,
	  toggle: false,
	  low: 33,
	  high: 66
    };
    $.extend(settings, options);

    var $svg = $(this), $background, $bar, $g, $text, timeout;

    function progressPercent(p){
      if(!$.isNumeric(p) || p < 0) {
        return 0;
      } else if(p > 100) {
        return 100;
      } else {
        return p;
      }
    }

    function getBarColor(displayedPercent){
  	var returnedCouleurValue = '#000000';
    	if(displayedPercent <= settings.low){
    		returnedCouleurValue = '#a00000';
    		}
    	else if (displayedPercent <= settings.high){
    		returnedCouleurValue = '#a05000';
    		}
    	else {
    		returnedCouleurValue = '#6ca000';
    	}
    	return returnedCouleurValue;
    }
    
    var Animate = {
      getWidth: function(){
    	  // a modifier pour pourcentages
        return (settings.parentWidth*settings.childSizePercent) * settings.percent / 100.00;
      },
      getPercent: function(currentWidth){
        returnedPercent = parseInt((100 * currentWidth / (settings.parentWidth*settings.childSizePercent)).toFixed(2));
		if(settings.toggle == true) {
        var colorV = getBarColor(returnedPercent);
		$bar.attr("fill", colorV);
		}
        return returnedPercent;
      },
      animateWidth: function(currentWidth, targetWidth){
        timeout = setTimeout(function(){
          if(currentWidth > targetWidth) {
            if(currentWidth - settings.increaseSpeed <= targetWidth) {
              currentWidth = targetWidth;
            } else {
              currentWidth = currentWidth - settings.increaseSpeed;
            }
          } else if(currentWidth < targetWidth) {
            if(currentWidth + settings.increaseSpeed >= targetWidth) {
              currentWidth = targetWidth;
            } else {
              currentWidth = currentWidth + settings.increaseSpeed;
            }
          }
          $bar.attr("width", ((currentWidth/settings.parentWidth)*(1/settings.childSizePercent)*100)+'%');
          $text.empty().append(Animate.getPercent(currentWidth) + "%");
          if(currentWidth != targetWidth) {
            Animate.animateWidth(currentWidth, targetWidth);
          }
        }, settings.increaseTime); 
      }
    }

    function svg(tag){
      return document.createElementNS("http://www.w3.org/2000/svg", tag);
    }
    !!function(){
      settings.percent = progressPercent(settings.percent);
      $svg.attr({'width': (settings.childSizePercent*100)+'%', 'height': settings.height});

      $background = $(svg("rect")).appendTo($svg)
                      .attr({x: 0, rx: settings.radius, width: '100%', height: settings.height, fill: settings.backgroundColor});

      $bar = $(svg("rect")).appendTo($svg)
                .attr({x: 0, rx: settings.radius, height: settings.height, fill: settings.barColor});

      $g = $(svg("g")).appendTo($svg)
                .attr({"fill": "#fff", "text-anchor": "middle", "font-family": "DejaVu Sans,Verdana,Geneva,sans-serif", "font-size": settings.fontSize});

      $text = $(svg("text")).appendTo($g)
                .attr({"x": "50%", "y": settings.height/2.0 + settings.fontSize/3.0, fill: settings.fontColor});

      draw();
    }();

    function draw() {
      var targetWidth = Animate.getWidth();
      if(settings.animate) {
        if(timeout) {
          clearTimeout(timeout);
        }
        var currentWidth = parseFloat($bar.attr("width"));
		
        if(!currentWidth) currentWidth = 0;

        Animate.animateWidth(currentWidth, targetWidth);
      } else {
        $bar.attr("width", targetWidth);
        $text.empty().append(settings.percent + "%");
      }
    }

    this.percent = function (p) {
      if(p) {
        p = progressPercent(p);

        settings.percent = p;
        draw();
      }
      return settings.percent;
    }
    return this;
  }
});
