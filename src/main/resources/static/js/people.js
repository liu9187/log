var eventjs = {
    //添加事件
    bind: function(elem, type, handler){
        if (elem.addEventListener) {
            elem.addEventListener(type, handler, false);
        } else if(elem.attachEvent){
            //IE
            elem.attachEvent("on" + type, handler);
        }else{
            elem["on" + type] = handler;
        };
    },
    //删除事件
    unbind: function(elem, type ,handler){
        if (elem.removeEventListener) {
            elem.removeEventListener(type, handler, false);
        } else if(elem.detachEvent){
            //IE
            elem.detachEvent("on" + type, handler)
        }else{
            elem["on" + type] = handler;
        };
    },
    //获取事件
    getEvent: function(event){
        return event ? event : window.event;
    },
    //获取事件目标
    getTarget: function(event){
        return event.target || event.srcElement;
    },
    //相关元素
    relatedTarget: function(event){
        if (event.relatedTarget) {
            return event.relatedTarget;
        } else if (event.toElement){
            return event.toElement;
        } else if (event.formElement) {
            return event.formElement;
        } else {
            return null;
        };
    },
    //阻止默认行为
    preventDefault: function(event){
        if(event.preventDefault){
            event.preventDefault();
        }else{
            event.returnValue = false;
        }
    },
    //取消捕获/冒泡
    cancelBubble: function(event){
        if (event.stopPropagation) {
            event.stopPropagation();
        } else{
            event.cancelBubble = true;
        };
    },
    //获取鼠标按钮
    getMouseButton: function(event){
        if (document.implementation.hasFeature("MouseEvents", "2.0")) {
            return event.button;
        } else{
            switch (event.button){
                case 0:
                case 1:
                case 3:
                case 5:
                case 7:
                    return 0;
                case 2:
                case 6:
                    return 2;
                case 4:
                    return 1;
            }
        };
    },
    //获取键盘code
    getCharCode: function(event){
        if (typeof event.charCode == "number") {
            console.log('char code');
            return event.charCode;
        } else{
            return event.keyCode;
        };
    },
    getDirection: function(event){
        var keyCode = event.which || event.keyCode;
        switch(keyCode){
            case 1:
            case 38:
            case 269: //up
                return 'up';
                break;
            case 2:
            case 40:
            case 270:
                return 'down';
                break;
            case 3:
            case 37:
            case 271:
                return 'left';
                break;
            case 4:
            case 39:
            case 272:
                return 'right';
                break;
            case 339: //exit
            case 240: //back
                return 'back';
                break;
        }
    }
};