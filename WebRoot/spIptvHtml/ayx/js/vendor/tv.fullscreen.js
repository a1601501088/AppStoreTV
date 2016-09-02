/**
 * Created by vincent ting on 15/1/12.
 */

(function (doc, _) {
    var w = this;
    var windowHeight,
        windowWidth,
        noop = function () {
        },
        container = doc.getElementById('pages-container'),
        wrapper = doc.getElementById('wrapper');
    w.getWindowSize = function (next) {
        if (windowHeight) {
            return next && next(windowWidth, windowHeight);
        }
        var width = w.innerWidth;
        if (width === 320) {
            return setTimeout(function () {
                w.getWindowSize(next);
            }, 200);
        }
        windowWidth = width;
        windowHeight = w.innerHeight;
        return getWindowSize(next);
    };
    // 全局远程接口
    w.location.replace('#0');
    // 当前开发者屏幕大小
    w.views = w.views || [];
    var console = w.console;
    var pages;
    var pageCount;
    window.currentPageId = 0;
    var doms2Arr = function (doms) {
        return Array.prototype.slice.call(doms);
    };
    if (!console) {
        console = {
            log: noop
        };
    } else if (!console.log) {
        console.log = noop;
    }
    // 为 string 增加 trim 方法
    String.prototype.trim = String.prototype.trim || function () {
        return this.replace(/^\s+|\s+$/gm, '');
    };
    // 获取 url 中 search 的参数
    var _getFromSearch = function (name) {
        var search = location.search.substr(1).split('&');
        for (var i = 0; search[i] !== undefined; i++) {
            if (search[i].indexOf(name + '=') !== -1) {
                return search[i].substr(name.length + 1);
            }
        }
        return false;
    };
    var debugMode = _getFromSearch('debug');
    // 获取 mac
    var mac;
    try {
        // 调用机顶盒中的获取 MAC 方法
        mac = w.jsCall.getTvMac();
    } catch (_) {
        if (!debugMode) {
            alert('This is a EGAME TV only page.');
            return;
        }
        mac = _getFromSearch('mac') || parseInt(Math.random() * 10000000, 10);
        console.log('Mac is now: ' + mac);
    }
    // 关掉活动页面
    w.closeWindow = function () {
        try {
            return w.jsCall.closeWindow();
        } catch (err) {
            console.log('Debug mode: Close window')
        }
    };
    // 打开游戏详情
    w.gameInfo = function (game_id, game_name, activity) {
        try {
            return w.jsCall.intoDetail(game_id, game_name, activity);
        } catch (err) {
            console.log('Debug mode: Game ' +
            Array.prototype.join.call(arguments, '|') + 'info will appear.')
        }
    };
    // AJAX 请求
    w.ajax = function (method, url, data, next) {
        var xhr, key;
        if (w.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        } else {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhr.target = url;
        xhr.overrideMimeType("application/json;charset=UTF-8");
        method = method.toUpperCase();
        var request_data = [];
        if (arguments.length === 3) {
            next = data;
            data = {};
        }
        data.mac = mac;
        data._ = Date.parse(new Date);
        for (key in data || {}) {
            if (data.hasOwnProperty(key)) {
                request_data.push(key + '=' + data[key]);
            }
        }
        switch (method) {
            case 'POST':
                xhr.open(method, url, true);
                xhr.setRequestHeader("content-type",
                    "application/x-www-form-urlencoded");
                xhr.send(request_data.join('&'));
                break;
            case 'GET':
                url += '?' + request_data.join('&');
                xhr.open(method, url, true);
                xhr.send(null);
                break;
        }
        xhr.onreadystatechange = function () {
            if (xhr.readyState !== 4) return;
            if (xhr.status === 200) {
                next.call(xhr, false, xhr.responseText);
                return
            }
            next.call(xhr, true, {
                status: xhr.status,
                text: xhr.responseText
            });
        };
    };
    // 事件委托
    w.on = function (event, className, next, context) {
        wrapper.addEventListener(event, function (e) {
            var target = e.target;
            while (true) {
                if (target.className.indexOf(className) !== -1) {
                    return next && next.call(context || target, e);
                }
                if (target === wrapper) return;
                target = target.parentNode;
            }
        });
    };    // 注册并获取到所有的 .page
    // 针对不同的按键绑定不同的事件
    var key_handler_map = {
        'keyup': [38, 40],
        'keydown': [37, 39]
    }, _btn_cache = {}, current_key_code;
    // 选择相应的按钮并设置为 focus
    // 并且设置当前聚焦
    var btnFocus = function (key) {
        var btn = _btn_cache[key];
        if (btn) {
            if (debugMode) {
                console.log(key, btn);
            }
            btn.focus();
            current_key_code = key;
        }
    };
    // 提前运算页面中每个可点击区域的情况
    var focusPreInit = function (parents) {
        // 清空旧数据
        for (var attr in _btn_cache) {
            if (_btn_cache.hasOwnProperty(attr)) {
                delete _btn_cache[attr];
            }
        }
        var tmp_btn_map = {};
        parents = parents || [doc];
        _.each(parents, function (p) {
            _.each(p.getElementsByClassName('key-btn'), function (btn) {
                var root_div = btn.parentNode.parentNode;
                if (root_div.attributes['data-non-focus'] &&
                    root_div.attributes['data-non-focus'].nodeValue) {
                    return
                }
                var index = btn.attributes['data-key'].nodeValue;
                tmp_btn_map[index] = btn;
            });
        });
        var addItem = function (i, j, btn) {
            var _j = j;
            // 如果被占用则相应后移
            while (true) {
                if (!_btn_cache[i + "'" + _j]) {
                    _btn_cache[i + "'" + _j] = btn;
                    break;
                }
                _j += 1;
            }
        };
        for (var i = 0; tmp_btn_map[i + "'0"]; i++) {
            for (var j = 0; tmp_btn_map[i + "'" + j]; j++) {
                var btn = tmp_btn_map[i + "'" + j];
                // 如果出现 data-col，相应向右多占 n - 1 个位置
                if (btn.attributes['data-col']) {
                    for (var m = 0; m < btn.attributes['data-col'].nodeValue - 1; m++) {
                        addItem(i, j + m, btn);
                    }
                }
                // 如果出现 data-row，相应向下多占 n - 1 个位置
                if (btn.attributes['data-row']) {
                    for (var n = 1; n < btn.attributes['data-row'].nodeValue; n++) {
                        addItem(i + n, j, btn);
                    }
                }
                addItem(i, j, btn);
            }
        }
        if (debugMode) {
            console.log(_btn_cache);
        }
    };
    // 根据当前位置移动按钮
    var moveKeyByStep = function (y, x) {
        var current_btn = _btn_cache[current_key_code];
        var key_arr = current_key_code.split("'");
        key_arr[0] -= -y;
        key_arr[1] -= -x;
        if (key_arr[1] <= 0) {
            return key_arr[0] + "'0";
        }
        var new_btn = _btn_cache[key_arr.join("'")];
        if (new_btn == current_btn) {
            if (y === 0) {
                while (true) {
                    key_arr[1] += x / Math.abs(x);
                    if (_btn_cache[key_arr.join("'")] != current_btn) {
                        return key_arr.join("'");
                    }
                }
            } else if (x === 0) {
                while (true) {
                    key_arr[0] += y / Math.abs(y);
                    if (_btn_cache[key_arr.join("'")] != current_btn) {
                        return key_arr.join("'");
                    }
                }
            }
            return;
        }
        if (!_btn_cache[key_arr.join("'")]) return moveKeyByStep(y, x - 1);
        return key_arr.join("'");
    };
    // 当前页面默认焦点
    w.btn_init_index = w.btn_init_index || "0'0";
    // 默认全局输入回调
    var input_fn = noop;
    // 覆盖全局输入回调
    w.oninput = function (next) {
        input_fn = next;
    };
    w.preventKeyHandler = false;
    var keyEventHandler = function (event) {
        if (w.preventKeyHandler) {
            event.preventDefault();
            return
        }
        // 兼容遥控器内容输入
        if (event.type === 'keyup' && event.keyCode > 47 && event.keyCode < 58) {
            input_fn((event.keyCode - 48).toString());
            return;
        }
        if (_.indexOf(key_handler_map[event.type], event.keyCode) !== -1) {
            event.preventDefault();
            if (!current_key_code) {
                return btnFocus(btn_init_index);
            }
            switch (event.keyCode) {
                case 37:
                    // 向左
                    return btnFocus(moveKeyByStep(0, -1));
                case 38:
                    // 向上
                    return btnFocus(moveKeyByStep(-1, 0));
                case 39:
                    // 向右
                    return btnFocus(moveKeyByStep(0, 1));
                case 40:
                    // 向下
                    return btnFocus(moveKeyByStep(1, 0));
            }
        }
    };
    // 绑定键盘事件
    var focusInit = function () {
        doc.body.addEventListener('keydown', keyEventHandler);
        doc.body.addEventListener('keyup', keyEventHandler);
    };
    var inputInit = function () {
        _.each(doc.getElementsByClassName('ipt-key'), function (btn) {
            btn.addEventListener('click', function () {
                var key = this.attributes['data-ipt'].nodeValue;
                input_fn(key);
            });
        });
    };
    // 清除 a>img 结构中多余的空格
    var pageTrim = function () {
        _.each(document.getElementsByTagName('a'), function (e) {
            _.each(e.childNodes, function (child_ele) {
                if (!child_ele.tagName && child_ele.nodeValue) {
                    child_ele.nodeValue = child_ele.nodeValue.trim();
                }
            });
        });
    };
    // 获取当前焦点
    w.currentInputIndex = function () {
        return current_key_code;
    };

    w.onload = function () {
        pageTrim();
        focusPreInit();
        focusInit();
        inputInit();
    };

}).call(window, document, _);
