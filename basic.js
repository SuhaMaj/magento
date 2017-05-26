var config = {
  enableEventTracking: false,
  edeEndpoint: 'https://www-apistress3.kohlsecommerce.com',
  logLevel: 0,
  templateConfiguration: {
    carousel: {
      // enablePriceValidation: true,
      forceStarSmall: false,
      showDoNotRecommend: true,
      showPercentageOff: true,
      showProductCode: true,
      showStarRating: false,
      showUserPreference: true  
    }
  }
};

var recommendationRenderer = new bd.RecommendationRenderer(config);
recommendationRenderer.setApiKey('JAAso4n7ibZpbRxsIZyo2ccVt3E7np1C')
.setChannel('TCom')
.setPage('PDP')
//   .setCcp('atgId', '2254000009113198')
.setCcp('productNumbers', '1000064')
//   .setCcp('rvProductNumbers', '1167424,2397792,2630235,2567107,825463,1431538,1944597,2116842,2116882,2330037,2410809,2476444,2519633,2531899,2554776,2563972,2565576,2575490,2590050,2618590,2637063')
//   .onProductClick(function (element, data) {
//     console.log('Product Click:', data);
//   })
//   .onSearchTermClick(function (element, data) {
//     console.log('Search Term Click:', data);
//   })
//   .onProductDislikeClick(function (element, data) {
//     console.log('Product Dislike Click:', data);
//   })
//   .onProductLikeClick(function (element, data) {
//     console.log('Product Like Click:', data);
//   })
//   .onProductNoPreferenceClick(function (element, data) {
//     console.log('Product No Preference Click:', data);
//   })
//   .onProductDoNotRecommendClick(function (element, data) {
//     console.log('Product Do Not Recommend Click:', data);
//   })
//   .onProductUserReviewClick(function (element, data) {
//     console.log('Product User Review Submit Click:', data);
//   })
//   .onProductCartAddClick(function (element, data) {
//     console.log('Product Cart Add Click:', data);
//   })
  .injectCombinedRecommendations();
