var validator = {
    isEmpty : function (selector, str) {
        str = str.toString();
        if (str.length > 0) {
            return true;
        } else {
            selector.attr("placeholder", "Invalid!");
            selector.addClass('plcholder');
            selector.css('color', 'red');
            return false;
        }
    },

    isAlpha : function (selector, str) {
        if (validator.isEmpty(selector, str)) {
            selector.removeClass('plcholder');
            selector.css('color', 'black');
            return /^[a-zA-Z()]+$/.test(str);
        } else {
            selector.attr("placeholder", "Invalid!");
            selector.addClass('plcholder');
            selector.css('color', 'red');
            return false;
        }
    },

    isNumber : function (selector, num) {
        num = parseInt(num);
        if (validator.isEmpty(selector, num)) {
            selector.removeClass('plcholder');
            selector.css('color', 'black');
            if (/^\d+$/.test(num)) {
                return true;
            } else {
            selector.attr("placeholder", "Invalid!");
            selector.addClass('plcholder');
            selector.css('color', 'red');
            console.log(typeof num);
            return false;
            }
        }
    },

    isEmail : function (selector, email) {
        if (validator.isEmpty(selector, email)) {
            selector.removeClass('plcholder');
            selector.css('color', 'black');
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (re.test(email)) {
                return true;
            } else {
                selector.attr("placeholder", "Invalid email!");
                selector.addClass('plcholder');
                selector.css('color', 'red');
                return false;
            }
        }
    }

};

var checkInput = {
    checkform : function () {
        var output = [];
        var fname = document.getElementById("fname").value;
        var lname = document.getElementById("lname").value;
        var email = document.getElementById("email").value;
        var number = document.getElementById("number").value;
        var bcountry = document.getElementById("billingcountry").value;
        var bcity = document.getElementById("billingcity").value;
        var bzip = document.getElementById("billingzip").value;
        var baddress = document.getElementById("billingaddress").value;
        var scountry = document.getElementById("shippingcountry").value;
        var scity = document.getElementById("shippingcity").value;
        var szip = document.getElementById("shippingzip").value;
        var saddress = document.getElementById("shippingaddress").value;
        // first name
        if (validator.isAlpha($('#fname'), fname)) {
            output.push(fname);
        }
        // last name
        if (validator.isAlpha($('#lname'), lname)) {
            output.push(lname);
        }
        // email
        if (validator.isEmail($('#email'), email)) {
            output.push(email);
        }
        // phone number
        if (validator.isNumber($('#number'), number)) {
            output.push(number);
        }
        // billing country
        if (validator.isAlpha($('#billingcountry'), bcountry)) {
            output.push(bcountry);
        }
        // billing city
        if (validator.isAlpha($('#billingcity'), bcity)) {
            output.push(bcity);
        }
        // billing zip code
        if (validator.isNumber($('#billingzip'), bzip)) {
            output.push(bzip);
        }
        // billing address
        if (validator.isEmpty($('#billingaddress'), baddress)) {
            output.push(baddress);
        }
        // shipping country
        if (validator.isAlpha($('#shippingcountry'), scountry)) {
            output.push(scountry);
        }
        // shippinh city
        if (validator.isAlpha($('#shippingcity'), scity)) {
            output.push(scity);
        }
        // shipping zip code
        if (validator.isNumber($('#shippingzip'), szip)) {
            output.push(szip);
        }
        // shipping address
        if (validator.isEmpty($('#shippingaddress'), saddress)) {
            output.push(saddress);
        }
        console.log(output);
        return output;

    }
};