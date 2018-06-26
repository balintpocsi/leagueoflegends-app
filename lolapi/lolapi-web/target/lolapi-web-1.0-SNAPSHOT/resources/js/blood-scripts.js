$(document).ready(function(){

    //Validation
    $('#login-form').submit(function(e) {
        
        var valid = true;

        var email = $('#login-form .valid-email').val();
        var name = $('#login-form .valid-name').val();
        console.log(name);
        console.log(email);
        
        

        $(".error").text('');

        if (email.length < 1) {
            $('#login-form .valid-email + .error').text('Required');
            valid = false;
        } else {
            var regEx_email = /^[1-9][0-9]?$|^100$/;
            var validEmail = regEx_email.test(email);
            if (!validEmail) {
                $('#login-form .valid-email + .error').text('Get the latest matches! Type in a number: 1-100');
                valid = false;
            }
        }
        
        if (name.length < 1) {
            $('#login-form .valid-name + .error').text('Required');
            valid = false;
        } else {
            var regEx_name = /^[a-z0-9]+(?:[ ][a-z0-9]+)*$/;
            var validName = regEx_name.test(name);
            console.log("name boolean: "+validName);
            if (!validName) {
                $('#login-form .valid-name + .error').text('Enter a valid name!');
                valid = false;
            }
        }

     
        
        if(!valid) {
            e.preventDefault();
        }
    });

   });