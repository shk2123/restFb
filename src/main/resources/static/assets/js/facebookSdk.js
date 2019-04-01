js.src = "https://connect.facebook.net/en_US/sdk.js";

$window.fbAsyncInit = function() {
    FB.init({ 
      appId: '2264590680271985',
      status: true, 
      cookie: true, 
      xfbml: true,
      version: 'v2.4'
    });
};