<!DOCTYPE html>
<html>
<head>
</head>
<body>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
$.post("http://119.23.250.138/weixin/getWxConfig",
	{
		url:location.href.split('#')[0]
	},function(result){
    	weixinConfig(result);
	}
);

function weixinConfig(result){
	var appId = result.appId;
	var timestamp = result.timestamp;
	var nonceStr = result.nonceStr;
	var signature = result.signature;
	console.log("appId=" + appId + ";timestamp=" + timestamp + ";nonceStr=" + nonceStr + ";signature=" + signature); 
	wx.config({
	    debug: true, 
	    appId: appId, 
	    timestamp: timestamp, 
	    nonceStr: nonceStr, 
	    signature: signature,
	    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] 
	});
	
	wx.ready(function(){
	    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareTimeline({
	        title: '第八篇 ：微信公众平台开发实战Java版之如何网页授权获取用户基本信息', // 分享标题
	        link:"http://www.cnblogs.com/liuhongfeng/p/5099149.html",
	        imgUrl: "http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_1442809977405.jpg", // 分享图标
	        success: function () { 
	        	alert('已分享到朋友圈');
	        },
	        cancel: function () { 
	           alert('取消分享到朋友圈');
	        }
	    });
	    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareAppMessage({
	        title: '第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息', // 分享标题
	        desc: "第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息", // 分享描述
	        link:"http://www.cnblogs.com/liuhongfeng/p/5057167.html",
	        imgUrl: "http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg", // 分享图标
	        type: 'link', // 分享类型,music、video或link，不填默认为link
	        success: function () { 
	        	alert('已分享到朋友');
	        },
	        cancel: function () { 
	           alert('取消分享到朋友');
	        }
	    });
	    
	    //获取“分享到QQ”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareQQ({
	        title: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享标题
	        desc: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享描述
	        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
	        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
	        success: function () { 
	           alert('已分享到QQ');
	        },
	        cancel: function () { 
	           alert('取消分享到QQ');
	        }
	    });
	    
	    //获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareWeibo({
	        title: '分享到腾讯微博标题', // 分享标题
	        desc: '分享到腾讯微博描述', // 分享描述
	        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
	        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
	        success: function () { 
	           alert('已分享到腾讯微博');
	        },
	        cancel: function () { 
	           alert('取消分享到腾讯微博');
	        }
	    });
	    
	    //获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareQZone({
	        title: '分享到QQ空间标题', // 分享标题
	        desc: '分享到QQ空间描述', // 分享描述
	        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
	        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
	        success: function () { 
	           alert('已分享到QQ空间');
	        },
	        cancel: function () { 
	           alert('取消分享到QQ空间');
	        }
	    });
	    
	});
}
</script>
</html>