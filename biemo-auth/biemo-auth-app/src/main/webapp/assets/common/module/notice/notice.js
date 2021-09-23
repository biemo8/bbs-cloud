/**
 * 消息通知
 *
 * @author  王帆
 * @version  1.0
 */

layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    var PLUGIN_NAME = 'iziToast';
    var iziToast = {};
    var supports = !!document.querySelector; // Feature test
    var isMobile = (/Mobi/.test(navigator.userAgent)) ? true : false;
    var mobileWidth = 568;
    var config = {};

    // Default settings
    var defaults = {
        class: '',
        title: '',
        message: '',
        color: '', // blue, red, green, yellow
        icon: '',
        iconText: '',
        iconColor: '',
        image: '',
        imageWidth: 50,
        layout: 1,
        balloon: false,
        close: true,
        rtl: false,
        position: 'bottomRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
        target: '',
        timeout: 5000,
        pauseOnHover: true,
        resetOnHover: false,
        progressBar: true,
        progressBarColor: '',
        animateInside: true,
        buttons: {},
        transitionIn: 'fadeInUp', // bounceInLeft, bounceInRight, bounceInUp, bounceInDown, fadeIn, fadeInDown, fadeInUp, fadeInLeft, fadeInRight, flipInX
        transitionOut: 'fadeOut', // fadeOut, fadeOutUp, fadeOutDown, fadeOutLeft, fadeOutRight, flipOutX
        transitionInMobile: 'fadeInUp',
        transitionOutMobile: 'fadeOutDown',
        onOpen: function () {
        },
        onClose: function () {
        },
        useAppend: false
    };

    //
    // Methods
    //


    /**
     * Polyfill for remove() method
     */
    if (!('remove' in Element.prototype)) {
        Element.prototype.remove = function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        };
    }

    /**
     * A simple forEach() implementation for Arrays, Objects and NodeLists
     * @private
     * @param {Array|Object|NodeList} collection Collection of items to iterate
     * @param {Function} callback Callback function for each iteration
     * @param {Array|Object|NodeList} scope Object/NodeList/Array that forEach is iterating over (aka `this`)
     */
    var forEach = function (collection, callback, scope) {
        if (Object.prototype.toString.call(collection) === '[object Object]') {
            for (var prop in collection) {
                if (Object.prototype.hasOwnProperty.call(collection, prop)) {
                    callback.call(scope, collection[prop], prop, collection);
                }
            }
        } else {
            if (collection) {
                for (var i = 0, len = collection.length; i < len; i++) {
                    callback.call(scope, collection[i], i, collection);
                }
            }
        }
    };

    /**
     * Merge defaults with user options
     * @private
     * @param {Object} defaults Default settings
     * @param {Object} options User options
     * @returns {Object} Merged values of defaults and options
     */
    var extend = function (defaults, options) {
        var extended = {};
        forEach(defaults, function (value, prop) {
            extended[prop] = defaults[prop];
        });
        forEach(options, function (value, prop) {
            extended[prop] = options[prop];
        });
        return extended;
    };

    /**
     * Get the closest matching element up the DOM tree
     * @param {Element} elem Starting element
     * @param {String} selector Selector to match against (class, ID, or data attribute)
     * @return {Boolean|Element} Returns false if not match found
     */
    var getClosest = function (elem, selector) {
        var firstChar = selector.charAt(0);
        for (; elem && elem !== document; elem = elem.parentNode) {
            if (firstChar === '.') {
                if (elem.classList.contains(selector.substr(1))) {
                    return elem;
                }
            } else if (firstChar === '#') {
                if (elem.id === selector.substr(1)) {
                    return elem;
                }
            } else if (firstChar === '[') {
                if (elem.hasAttribute(selector.substr(1, selector.length - 2))) {
                    return elem;
                }
            }
        }
        return false;
    };

    /**
     * animationEnd
     * @private
     */
    function whichAnimationEvent() {
        var t,
            el = document.createElement("fakeelement");

        var animations = {
            "animation": "animationend",
            "OAnimation": "oAnimationEnd",
            "MozAnimation": "animationend",
            "WebkitAnimation": "webkitAnimationEnd"
        };
        for (t in animations) {
            if (el.style[t] !== undefined) {
                return animations[t];
            }
        }
    }

    var animationEvent = whichAnimationEvent();

    /**
     * Create a fragment DOM elements
     * @private
     */
    function createFragElem(htmlStr) {
        var frag = document.createDocumentFragment(),
            temp = document.createElement('div');
        temp.innerHTML = htmlStr;
        while (temp.firstChild) {
            frag.appendChild(temp.firstChild);
        }
        return frag;
    }

    /**
     * Do the calculation to move the progress bar
     * @private
     */
    function moveProgress(toast, settings, callback) {

        var isPaused = false;
        var isReseted = false;
        var isClosed = false;
        var timerTimeout = null;
        var elem = toast.querySelector("." + PLUGIN_NAME + "-progressbar div");
        var progressBar = {
            hideEta: null,
            maxHideTime: null,
            currentTime: new Date().getTime(),
            updateProgress: function () {
                isPaused = toast.classList.contains(PLUGIN_NAME + '-paused') ? true : false;
                isReseted = toast.classList.contains(PLUGIN_NAME + '-reseted') ? true : false;
                isClosed = toast.classList.contains(PLUGIN_NAME + '-closed') ? true : false;

                //console.log(new Date().getTime());

                if (isReseted) {
                    //console.log('ok');
                    clearTimeout(timerTimeout);
                    elem.style.width = '100%';
                    moveProgress(toast, settings, callback);
                    toast.classList.remove(PLUGIN_NAME + '-reseted');
                }
                if (isClosed) {
                    clearTimeout(timerTimeout);
                    //console.log('closed1');
                    toast.classList.remove(PLUGIN_NAME + '-closed');
                }

                if (!isPaused && !isReseted && !isClosed) {
                    progressBar.currentTime = progressBar.currentTime + 10;
                    var percentage = ((progressBar.hideEta - (progressBar.currentTime)) / progressBar.maxHideTime) * 100;
                    elem.style.width = percentage + '%';

                    if (Math.round(percentage) < 0 || typeof toast != 'object') {
                        clearTimeout(timerTimeout);
                        callback.apply();
                        //console.log('closed2');
                    }
                }

            }
        };
        if (settings.timeout > 0) {
            progressBar.maxHideTime = parseFloat(settings.timeout);
            progressBar.hideEta = new Date().getTime() + progressBar.maxHideTime;
            timerTimeout = setInterval(progressBar.updateProgress, 10);
        }

    }

    /**
     * Destroy the current initialization.
     * @public
     */
    iziToast.destroy = function () {
        layer.closeAll('loading');

        forEach(document.querySelectorAll('.' + PLUGIN_NAME + '-wrapper'), function (element, index) {
            element.remove();
        });

        forEach(document.querySelectorAll('.' + PLUGIN_NAME), function (element, index) {
            element.remove();
        });

        // Remove event listeners
        document.removeEventListener(PLUGIN_NAME + '-open', {}, false);
        document.removeEventListener(PLUGIN_NAME + '-close', {}, false);

        // Reset variables
        config = {};
    };

    /**
     * Initialize Plugin
     * @public
     * @param {Object} options User settings
     */
    iziToast.settings = function (options) {

        // feature test
        if (!supports) return;

        // Destroy any existing initializations
        iziToast.destroy();

        config = options;
        defaults = extend(defaults, options || {});
    };

    /**
     * Info theme
     * @public
     * @param {Object} options User settings
     */
    iziToast.info = function (options) {

        var theme = {
            color: "blue",
            icon: "ico-info"
        };

        var settings = extend(config, options || {});
        settings = extend(theme, settings || {});

        this.show(settings);
    };

    /**
     * Success theme
     * @public
     * @param {Object} options User settings
     */
    iziToast.success = function (options) {

        var theme = {
            color: "green",
            icon: "ico-check"
        };

        var settings = extend(config, options || {});
        settings = extend(theme, settings || {});

        this.show(settings);
    };

    /**
     * Warning theme
     * @public
     * @param {Object} options User settings
     */
    iziToast.warning = function (options) {

        var theme = {
            color: "yellow",
            icon: "ico-warning"
        };

        var settings = extend(config, options || {});
        settings = extend(theme, settings || {});

        this.show(settings);
    };

    /**
     * Error theme
     * @public
     * @param {Object} options User settings
     */
    iziToast.error = function (options) {

        var theme = {
            color: "red",
            icon: "ico-error"
        };

        var settings = extend(config, options || {});
        settings = extend(theme, settings || {});

        this.show(settings);
    };

    /**
     * Close the specific Toast
     * @public
     * @param {Object} options User settings
     */
    iziToast.hide = function (options, $toast) {

        var settings = extend(defaults, options || {});

        if (typeof $toast != 'object') {
            $toast = document.querySelector($toast);
        }
        $toast.classList.add(PLUGIN_NAME + '-closed');

        if (settings.transitionIn || settings.transitionInMobile) {
            $toast.classList.remove(settings.transitionIn, settings.transitionInMobile);
        }
        if (settings.transitionOut || settings.transitionOutMobile) {

            if (isMobile || window.innerWidth <= mobileWidth) {
                if (settings.transitionOutMobile.length > 0)
                    $toast.classList.add(settings.transitionOutMobile);
            } else {
                if (settings.transitionOut.length > 0)
                    $toast.classList.add(settings.transitionOut);
            }
            var H = $toast.parentNode.offsetHeight;
            $toast.parentNode.style.height = H + 'px';
            $toast.style.pointerEvents = 'none';
            if (isMobile || window.innerWidth <= mobileWidth) {

            } else {
                $toast.parentNode.style.transitionDelay = '0.2s';
            }

            setTimeout(function () {
                $toast.parentNode.style.height = '0px';
                window.setTimeout(function () {
                    $toast.parentNode.remove();
                }, 1000);
            }, 200);

        } else {
            $toast.parentNode.remove();
        }

        if (settings.class) {
            try {
                var event;
                if (window.CustomEvent) {
                    event = new CustomEvent('iziToast-close', {detail: {class: settings.class}});
                } else {
                    event = document.createEvent('CustomEvent');
                    event.initCustomEvent('iziToast-close', true, true, {class: settings.class});
                }
                document.dispatchEvent(event);
            } catch (ex) {
                console.warn(ex);
            }
        }

        if (typeof settings.onClose !== "undefined")
            settings.onClose.apply();
    };

    /**
     * Create and show the Toast
     * @public
     * @param {Object} options User settings
     */
    iziToast.show = function (options) {

        var that = this;

        // Merge user options with defaults
        var settings = extend(config, options || {});
        settings = extend(defaults, settings);

        var $toastCapsule = document.createElement("div");
        $toastCapsule.classList.add(PLUGIN_NAME + "-capsule");

        var $toast = document.createElement("div");
        $toast.classList.add(PLUGIN_NAME);

        if (isMobile || window.innerWidth <= mobileWidth) {
            if (settings.transitionInMobile.length > 0)
                $toast.classList.add(settings.transitionInMobile);
        } else {
            if (settings.transitionIn.length > 0)
                $toast.classList.add(settings.transitionIn);
        }

        if (settings.rtl) {
            $toast.classList.add(PLUGIN_NAME + '-rtl');
        }

        if (settings.color.length > 0) { //#, rgb, rgba, hsl

            if (settings.color.substring(0, 1) == "#" || settings.color.substring(0, 3) == "rgb" || settings.color.substring(0, 3) == "hsl") {
                $toast.style.background = settings.color;
            } else {
                $toast.classList.add(PLUGIN_NAME + '-color-' + settings.color);
            }
        }

        if (settings.class) {
            $toast.classList.add(settings.class);
        }

        if (settings.image) {
            var $cover = document.createElement("div");
            $cover.classList.add(PLUGIN_NAME + '-cover');
            $cover.style.width = settings.imageWidth + "px";
            $cover.style.backgroundImage = 'url(' + settings.image + ')';
            $toast.appendChild($cover);
        }

        var $buttonClose;
        if (settings.close) {
            $buttonClose = document.createElement("button");
            $buttonClose.classList.add(PLUGIN_NAME + '-close');
            $toast.appendChild($buttonClose);
        } else {
            if (settings.rtl) {
                $toast.style.paddingLeft = "30px";
            } else {
                $toast.style.paddingRight = "30px";
            }
        }

        if (settings.progressBar) {

            var $progressBar = document.createElement("div");
            $progressBar.classList.add(PLUGIN_NAME + '-progressbar');

            var $progressBarDiv = document.createElement("div");
            $progressBarDiv.style.background = settings.progressBarColor;

            $progressBar.appendChild($progressBarDiv);
            $toast.appendChild($progressBar);

            setTimeout(function () {
                moveProgress($toast, settings, function () {
                    that.hide(settings, $toast);
                });
            }, 300);
        }
        else if (settings.progressBar === false && settings.timeout > 0) {
            setTimeout(function () {
                that.hide(settings, $toast);
            }, settings.timeout);
        }


        var $toastBody = document.createElement("div");
        $toastBody.classList.add(PLUGIN_NAME + '-body');

        if (settings.image) {
            if (settings.rtl) {
                $toastBody.style.marginRight = (settings.imageWidth + 10) + 'px';
            } else {
                $toastBody.style.marginLeft = (settings.imageWidth + 10) + 'px';
            }
        }

        if (settings.icon) {
            var $icon = document.createElement("i");
            $icon.setAttribute("class", PLUGIN_NAME + '-icon ' + settings.icon);

            if (settings.iconText) {
                $icon.appendChild(document.createTextNode(settings.iconText));
            }

            if (settings.rtl) {
                $toastBody.style.paddingRight = '33px';
            } else {
                $toastBody.style.paddingLeft = '33px';
            }

            if (settings.iconColor) {
                $icon.style.color = settings.iconColor;
            }
            $toastBody.appendChild($icon);
        }

        var $strong = document.createElement("strong");
        $strong.appendChild(document.createTextNode(settings.title));

        var $p = document.createElement("p");
        $p.appendChild(document.createTextNode(settings.message));


        if (settings.layout > 1) {
            $p.style.width = "100%";
            $toast.classList.add(PLUGIN_NAME + "-layout" + settings.layout);
        }

        if (settings.balloon) {
            $toast.classList.add(PLUGIN_NAME + "-balloon");
        }

        $toastBody.appendChild($strong);
        $toastBody.appendChild($p);

        var $buttons;
        if (settings.buttons.length > 0) {

            $buttons = document.createElement("div");
            $buttons.classList.add(PLUGIN_NAME + '-buttons');

            $p.style.marginRight = '15px';

            var i = 0;
            forEach(settings.buttons, function (value, index) {
                $buttons.appendChild(createFragElem(value[0]));

                var $btns = $buttons.childNodes;

                $btns[i].addEventListener('click', function (event) {
                    event.preventDefault();
                    var ts = value[1];
                    return new ts(that, $toast);
                });

                i++;
            });

            $toastBody.appendChild($buttons);
        }

        $toast.appendChild($toastBody);
        $toastCapsule.style.visibility = 'hidden';
        $toastCapsule.style.height = '0px';
        $toastCapsule.appendChild($toast);

        setTimeout(function () {
            var H = $toast.offsetHeight;
            var style = $toast.currentStyle || window.getComputedStyle($toast);
            var marginTop = style.marginTop;
            marginTop = marginTop.split("px");
            marginTop = parseInt(marginTop[0]);
            var marginBottom = style.marginBottom;
            marginBottom = marginBottom.split("px");
            marginBottom = parseInt(marginBottom[0]);

            $toastCapsule.style.visibility = '';
            $toastCapsule.style.height = (H + marginBottom + marginTop) + 'px';
            setTimeout(function () {
                $toastCapsule.style.height = 'auto';
            }, 1000);
        }, 100);

        var position = settings.position,
            $wrapper;


        if (settings.target) {

            $wrapper = document.querySelector(settings.target);
            $wrapper.classList.add(PLUGIN_NAME + '-target');
            $wrapper.appendChild($toastCapsule);

        } else {
            if (isMobile || window.innerWidth <= mobileWidth) {
                if (settings.position == "bottomLeft" || settings.position == "bottomRight" || settings.position == "bottomCenter") {
                    position = PLUGIN_NAME + '-wrapper-bottomCenter';
                }
                else if (settings.position == "topLeft" || settings.position == "topRight" || settings.position == "topCenter") {
                    position = PLUGIN_NAME + '-wrapper-topCenter';
                }
                else {
                    position = PLUGIN_NAME + '-wrapper-center';
                }
            } else {
                position = PLUGIN_NAME + '-wrapper-' + position;
            }
            $wrapper = document.querySelector('.' + PLUGIN_NAME + '-wrapper.' + position);

            if (!$wrapper) {
                $wrapper = document.createElement("div");
                $wrapper.classList.add(PLUGIN_NAME + '-wrapper');
                $wrapper.classList.add(position);
                document.body.appendChild($wrapper);
            }
            if ((settings.position == "topLeft" || settings.position == "topCenter" || settings.position == "topRight") && !settings.useAppend) {

                $wrapper.insertBefore($toastCapsule, $wrapper.firstChild);
            } else {
                $wrapper.appendChild($toastCapsule);
            }
        }

        settings.onOpen.apply();

        try {
            var event;
            if (window.CustomEvent) {
                event = new CustomEvent('iziToast-open', {detail: {class: settings.class}});
            } else {
                event = document.createEvent('CustomEvent');
                event.initCustomEvent('iziToast-open', true, true, {class: settings.class});
            }
            document.dispatchEvent(event);
        } catch (ex) {
            console.warn(ex);
        }

        if (settings.animateInside) {
            $toast.classList.add(PLUGIN_NAME + '-animateInside');

            var timeAnimation1 = 200;
            var timeAnimation2 = 100;
            var timeAnimation3 = 300;
            if (settings.transitionIn == "bounceInLeft") {
                timeAnimation1 = 400;
                timeAnimation2 = 200;
                timeAnimation3 = 400;
            }

            window.setTimeout(function () {
                $strong.classList.add('slideIn');
            }, timeAnimation1);

            window.setTimeout(function () {
                $p.classList.add('slideIn');
            }, timeAnimation2);

            if (settings.icon) {
                window.setTimeout(function () {
                    $icon.classList.add('revealIn');
                }, timeAnimation3);
            }

            if (settings.buttons.length > 0 && $buttons) {
                var counter = 150;
                forEach($buttons.childNodes, function (element, index) {

                    window.setTimeout(function () {
                        element.classList.add('revealIn');
                    }, counter);
                    counter = counter + counter;
                });
            }
        }

        if ($buttonClose) {
            $buttonClose.addEventListener('click', function (event) {
                var button = event.target;
                that.hide(settings, $toast);
            });
        }

        if (settings.pauseOnHover) {

            $toast.addEventListener('mouseenter', function (event) {
                this.classList.add(PLUGIN_NAME + '-paused');
            });
            $toast.addEventListener('mouseleave', function (event) {
                this.classList.remove(PLUGIN_NAME + '-paused');
            });
        }
        if (settings.resetOnHover) {

            $toast.addEventListener('mouseenter', function (event) {
                this.classList.add(PLUGIN_NAME + '-reseted');
            });
            $toast.addEventListener('mouseleave', function (event) {
                this.classList.remove(PLUGIN_NAME + '-reseted');
            });
        }
    };

    iziToast.msg = function (msg, options) {
        var timeout = 3000;
        if (options.icon == 4) {
            timeout = 150000;
            layer.load(1, {
                success: function (layero, index) {
                    $(layero).css('display', 'none')
                }
            });
        }

        var icons = ['ico-msgOk', 'ico-msgError', 'ico-msgWran', 'ico-msgLoad'];
        options.icon = icons[options.icon - 1];
        var theme = {
            title: '',
            message: msg,
            position: 'topCenter',
            transitionIn: 'fadeInDown',
            transitionOut: 'fadeOutUp',
            progressBar: false,
            animateInside: false,
            close: false,
            class: 'msg',
            timeout: timeout,
            useAppend: true
        };

        var settings = extend(config, options || {});
        settings = extend(theme, settings || {});

        this.show(settings);
    };


    layui.link(layui.cache.base + 'notice/notice.css');

    exports('notice', iziToast);
});