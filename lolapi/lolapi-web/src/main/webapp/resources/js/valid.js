/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//http://form.guide/jquery/validation-using-jquery-examples.html
$(document).ready(function() {

  $('#first_form').submit(function(e) {
    e.preventDefault();                   
    
    var email = $('#email').val();
    

    $(".error").remove();                 

   
    if (email.length < 1) {
      $('#email').after('<span class="error">This field is required</span>');
    } else {
      var regEx = /^[1-9][0-9]?$|^100$/;
      var validEmail = regEx.test(email);
      if (!validEmail) {
        $('#email').after('<span class="error">Enter a valid email</span>');
      }
    }
   
  });



});

