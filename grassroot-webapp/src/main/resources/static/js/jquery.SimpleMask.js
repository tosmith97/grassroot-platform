<script>
<!--
document.write(unescape("String.prototype.simpleMaskStringCount%20%3D%20function%28s1%29%20%7B%20return%20%28this.length%20-%20this.replace%28new%20RegExp%28s1%2C%22g%22%29%2C%20%27%27%29.length%29%20/%20s1.length%3B%20%7D%3B%0A%0A%28function%28%24%29%7B%0A%0A%09var%20defaults%20%3D%0A%09%7B%0A%09%09mask%3A%20%27%27%2C%0A%09%09nextInput%3A%20null%2C%0A%09%09onComplete%20%3A%20null%0A%09%7D%3B%0A%09var%20objects%20%3D%20%5B%5D%3B%0A%0A%09var%20methods%20%3D%0A%09%7B%0A%09%09init%20%3A%20function%28options%29%0A%09%09%7B%0A%09%09%09var%20opts%20%3D%20%24.extend%28%20%7B%7D%2C%20defaults%2C%20options%20%29%3B%0A%0A%09%09%09return%20this.each%28function%28%29%0A%09%09%09%7B%0A%09%09%09%09%24.fn.simpleMask.process%28%24%28this%29%2C%20opts%29%3B%0A%09%09%09%7D%29%3B%0A%09%09%7D%2C%0A%09%09unmask%20%3A%20function%28%29%20%7B%20return%20this.each%28function%28%29%20%7B%20%24.fn.simpleMask.unmask%28this%29%3B%20%7D%29%3B%20%7D%2C%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask%20%3D%20function%28methodOrOptions%29%0A%09%7B%0A%09%09if%20%28%20methods%5BmethodOrOptions%5D%20%29%0A%09%09%7B%0A%09%09%09return%20methods%5B%20methodOrOptions%20%5D.apply%28%20this%2C%20Array.prototype.slice.call%28%20arguments%2C%201%20%29%29%3B%0A%09%09%7D%0A%09%09else%20if%20%28%20typeof%20methodOrOptions%20%3D%3D%3D%20%27object%27%20%7C%7C%20%21%20methodOrOptions%20%29%0A%09%09%7B%0A%09%09%09return%20methods.init.apply%28%20this%2C%20arguments%20%29%3B%0A%09%09%7D%0A%09%09else%0A%09%09%7B%0A%09%09%09%24.error%28%27Method%20%27%20+%20methodOrOptions%20+%20%27%20does%20not%20exist%20on%20jQuery.simpleMask%27%29%3B%0A%09%09%7D%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.makeId%20%3D%20function%28%29%0A%09%7B%0A%09%09var%20text%20%3D%20%22%22%3B%0A%09%09var%20possible%20%3D%20%220123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz0123456789%22%3B%0A%09%09var%20pl%20%3D%20possible.length%3B%0A%09%09for%28var%20i%20%3D%200%3B%20i%20%3C%208%3B%20i++%29%0A%09%09%7B%0A%09%09%09text%20+%3D%20possible.charAt%28Math.floor%28Math.random%28%29%20*%20pl%29%29%3B%0A%09%09%7D%0A%09%09return%20text%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask._onComplete%20%3D%20function%28p_arg%29%0A%09%7B%0A%09%09var%20ids%20%3D%20%28typeof%28p_arg%29%20%3D%3D%20%27object%27%29%20%3F%20%24%28p_arg%29.attr%28%27data-mask-ids%27%29%20%3A%20p_arg%3B%0A%09%09if%20%28objects%5Bids%5D.options.onComplete%20%21%3D%3D%20null%29%0A%09%09%7B%0A%09%09%09objects%5Bids%5D.options.onComplete.call%28this%2C%20objects%5Bids%5D%29%3B%0A%09%09%09%24.fn.simpleMask._nextInput%28ids%29%3B%0A%09%09%7D%0A%09%09else%0A%09%09%7B%0A%09%09%09%24.fn.simpleMask._nextInput%28ids%29%3B%0A%09%09%7D%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.nextOnTabIndex%20%3D%20function%28element%29%0A%09%7B%0A%09%09var%20fields%20%3D%20%24%28%24%28%27form%27%29%0A%09%09%09.find%28%27input%2C%20select%2C%20textarea%27%29%0A%09%09%09.filter%28%27%3Avisible%27%29.filter%28%27%3Aenabled%27%29%0A%09%09%09.toArray%28%29%0A%09%09%09.sort%28function%28a%2C%20b%29%20%7B%0A%09%09%09return%20%28%28a.tabIndex%20%3E%200%29%20%3F%20a.tabIndex%20%3A%201000%29%20-%20%28%28b.tabIndex%20%3E%200%29%20%3F%20b.tabIndex%20%3A%201000%29%3B%0A%09%09%09%7D%29%29%0A%09%09%3B%0A%09%09return%20fields.eq%28%28fields.index%28element%29%20+%201%29%20%25%20fields.length%29%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask._nextInput%20%3D%20function%28p_arg%29%0A%09%7B%0A%09%09var%20ids%20%3D%20%28typeof%28p_arg%29%20%3D%3D%20%27object%27%29%20%3F%20%24%28p_arg%29.attr%28%27data-mask-ids%27%29%20%3A%20p_arg%3B%0A%09%09if%20%28objects%5Bids%5D.options.nextInput%20%21%3D%3D%20null%29%0A%09%09%7B%0A%09%09%09if%20%28objects%5Bids%5D.options.nextInput%20%3D%3D%3D%20true%29%0A%09%09%09%7B%0A%09%09%09%09var%20nextelement%20%3D%20%24.fn.simpleMask.nextOnTabIndex%28objects%5Bids%5D.element%29%3B%0A%09%09%09%09if%20%28nextelement.length%20%3E%200%29%0A%09%09%09%09%7B%0A%09%09%09%09%09nextelement.focus%28%29%3B%0A%09%09%09%09%7D%0A%09%09%09%7D%0A%09%09%09else%20if%20%28objects%5Bids%5D.options.nextInput.length%20%3E%200%29%0A%09%09%09%7B%0A%09%09%09%09objects%5Bids%5D.options.nextInput.focus%28%29.select%28%29%3B%0A%09%09%09%7D%0A%09%09%7D%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.unmask%20%3D%20function%28p_arg%29%0A%09%7B%0A%09%09var%20ids%20%3D%20%28typeof%28p_arg%29%20%3D%3D%20%27object%27%29%20%3F%20%24%28p_arg%29.attr%28%27data-mask-ids%27%29%20%3A%20p_arg%3B%0A%0A%09%09%24%28objects%5Bids%5D.element%29.removeClass%28%27input-masked%27%29.removeAttr%28%27data-mask-ids%27%29%3B%0A%09%09if%20%28%20%24%28objects%5Bids%5D.element%29.attr%28%27class%27%29%20%3D%3D%3D%20%27%27%20%29%0A%09%09%7B%0A%09%09%09%24%28objects%5Bids%5D.element%29.removeAttr%28%27class%27%29%3B%0A%09%09%7D%0A%0A%09%09%24%28document%29.off%0A%09%09%28%0A%09%09%09%27keyup.simpleMask%20change.simpleMask%27%2C%0A%09%09%09%27input%5Bdata-mask-ids%3D%22%27%20+%20ids%20+%20%27%22%5D%27%0A%09%09%29%3B%0A%09%09%24%28document%29.off%0A%09%09%28%0A%09%09%09%27keydown.simpleMask%27%2C%0A%09%09%09%27input%5Bdata-mask-ids%3D%22%27%20+%20ids%20+%20%27%22%5D%27%0A%09%09%29%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.isNumber%20%3D%20function%28p_string%29%0A%09%7B%0A%09%09return%20p_string.replace%28/%5CD/g%2C%20%27%27%29%20%21%3D%3D%20%27%27%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.onlyNumbers%20%3D%20function%28p_string%29%0A%09%7B%0A%09%09return%20p_string.replace%28/%5CD/g%2C%20%27%27%29%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.onlyNumbersLength%20%3D%20function%28p_string%29%0A%09%7B%0A%09%09return%20p_string.replace%28/%5CD/g%2C%20%27%27%29.length%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.applyMask%20%3D%20function%28p_object%29%0A%09%7B%0A%09%09var%20p_element%20%3D%20p_object.element%3B%0A%09%09var%20html_element%20%3D%20%24%28p_element%29%5B0%5D%3B%0A%09%09var%20caret_ini%20%3D%20html_element.selectionStart%3B%0A%09%09var%20caret_end%20%3D%20html_element.selectionEnd%3B%0A%09%09var%20old_value%20%3D%20p_object.oldvalue%3B%0A%09%09var%20cur_value%20%3D%20%24%28p_element%29.val%28%29%3B%0A%09%09var%20vrTemp%20%3D%20%24.fn.simpleMask.onlyNumbers%28%24%28p_element%29.val%28%29%29%3B%0A%0A%09%09var%20p_mask%20%3D%20p_object.masks%5B0%5D%3B%0A%09%09var%20max_mask%20%3D%20p_object.masks%5Bp_object.masks.length-1%5D.simpleMaskStringCount%28%27%23%27%29%3B%0A%09%09if%20%28vrTemp.length%20%3E%20max_mask%29%0A%09%09%7B%0A%09%09%09vrTemp%20%3D%20vrTemp.substr%280%2C%20max_mask%29%3B%0A%09%09%7D%0A%09%09var%20curr_length%20%3D%20vrTemp.length%3B%0A%09%09for%28var%20i%20in%20p_object.masks%29%0A%09%09%7B%0A%09%09%09if%20%28p_object.masks%5Bi%5D.simpleMaskStringCount%28%27%23%27%29%20%3D%3D%20curr_length%29%0A%09%09%09%7B%0A%09%09%09%09p_mask%20%3D%20p_object.masks%5Bi%5D%3B%0A%09%09%09%09break%3B%0A%09%09%09%7D%0A%09%09%7D%0A%0A%09%09if%20%28vrTemp.length%20%3E%200%29%0A%09%09%7B%0A%09%09%09vrTemp%20%3D%20vrTemp.trim%28%29%3B%0A%09%09%09var%20result%20%3D%20p_mask%3B%0A%09%09%09var%20l%20%3D%20vrTemp.length%3B%0A%09%09%09for%20%28var%20k%20%3D%200%3B%20k%20%3C%20l%3B%20k++%29%0A%09%09%09%7B%0A%09%09%20%20%20%20%09result%20%3D%20result.replace%28%27%23%27%2C%20vrTemp.charAt%28k%29%29%3B%0A%09%09%09%7D%0A%09%09%09var%20pos%20%3D%20result.indexOf%28%27%23%27%29%3B%0A%09%09%09if%20%28pos%20%21%3D%20-1%29%0A%09%09%09%7B%0A%09%09%09%09result%20%3D%20result.substr%280%2C%20pos%29%3B%0A%09%09%09%7D%0A%09%09%09var%20ultimo%20%3D%20result.substr%28result.length-1%2C%201%29%3B%0A%09%09%09if%20%28%24.fn.simpleMask.onlyNumbers%28ultimo%29%20%3D%3D%3D%20%27%27%29%0A%09%09%09%7B%0A%09%09%09%09result%20%3D%20result.substr%280%2C%20pos-1%29%3B%0A%09%09%09%7D%0A%09%09%09%0A%09%09%09var%20lastchar%20%3D%20result.substr%28result.length%20-%201%2C%201%29%3B%0A%09%09%09while%28%20%28result.length%20%3E%200%29%20%26%26%20%28%20%24.fn.simpleMask.isNumber%28lastchar%29%20%3D%3D%3D%20false%20%29%20%29%0A%09%09%09%7B%0A%09%09%09%09result%20%3D%20result.substr%280%2C%20result.length%20-%201%29%3B%0A%09%09%09%09lastchar%20%3D%20result.substr%28result.length%20-%201%2C%201%29%3B%0A%09%09%09%7D%0A%0A%09%09%09if%20%28result%20%21%3D%20cur_value%29%0A%09%09%09%7B%0A%09%09%09%09%24%28p_element%29.val%28result%29%3B%0A%09%09%09%7D%0A%09%09%09if%20%28result%20%21%3D%20old_value%29%0A%09%09%09%7B%0A%09%09%09%09if%20%28%20%28result.length%20%3D%3D%20p_mask.length%29%20%26%26%20%28%20result.length%20%3D%3D%20caret_end%20%29%20%26%26%20%28%20result.length%20%3D%3D%20p_object.maxlengthmask%20%29%20%20%29%0A%09%09%09%09%7B%0A%09%09%09%09%09%24.fn.simpleMask._onComplete%28p_element.attr%28%27data-mask-ids%27%29%29%3B%0A%09%09%09%09%7D%0A%09%09%09%7D%0A%09%09%7D%0A%09%09else%0A%09%09%7B%0A%09%09%09%24%28p_element%29.val%28%27%27%29%3B%0A%09%09%7D%0A%0A%09%09p_object.oldvalue%20%3D%20%24%28p_element%29.val%28%29%3B%0A%09%7D%3B%0A%0A%09%24.fn.simpleMask.process%20%3D%20function%28p_elem%2C%20p_options%29%0A%09%7B%0A%09%09var%20ids%20%3D%20%24.fn.simpleMask.makeId%28%29%3B%0A%09%09while%20%28objects%5Bids%5D%20%21%3D%3D%20undefined%29%0A%09%09%7B%0A%09%09%09ids%20%3D%20%24.fn.simpleMask.makeId%28%29%3B%0A%09%09%7D%0A%09%09var%20comp%20%3D%20%7B%7D%3B%0A%09%09comp.element%20%20%20%20%3D%20p_elem%3B%0A%09%09comp.options%20%20%20%20%3D%20p_options%3B%0A%09%09comp.nextInput%20%20%3D%20p_options.nextInput%3B%0A%09%09comp.onComplete%20%3D%20p_options.onComplete%3B%0A%09%09comp.oldvalue%20%20%20%3D%20%24%28p_elem%29.val%28%29%3B%0A%0A%09%09var%20usemasks%20%3D%20%5B%5D%3B%0A%09%09if%20%28typeof%20p_options.mask%20%3D%3D%20%27string%27%29%0A%09%09%7B%0A%09%09%09usemasks%20%3D%20%5Bp_options.mask%5D%3B%0A%09%09%7D%0A%09%09else%0A%09%09%7B%0A%09%09%09usemasks%20%3D%20p_options.mask%3B%0A%09%09%7D%0A%0A%09%09for%20%28var%20k%20in%20usemasks%29%0A%09%09%7B%0A%09%09%09switch%28usemasks%5Bk%5D.toLowerCase%28%29%29%0A%09%09%09%7B%0A%09%09%09%09case%20%27cpf%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23%23.%23%23%23.%23%23%23-%23%23%27%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27cnpj%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23.%23%23%23.%23%23%23/%23%23%23%23-%23%23%27%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27cep%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23%23%23%23-%23%23%23%27%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27date%27%3A%0A%09%09%09%09case%20%27data%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23/%23%23/%23%23%23%23%27%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27telefone%27%3A%0A%09%09%09%09case%20%27tel%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23%23%23-%23%23%23%23%27%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27telefone9%27%3A%0A%09%09%09%09case%20%27tel9%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%23%23%23%23-%23%23%23%23%27%3B%0A%09%09%09%09%09usemasks.push%28%27%23%23%23%23%23-%23%23%23%23%27%29%3B%0A%09%09%09%09break%3B%0A%09%09%09%09case%20%27ddd-telefone9%27%3A%0A%09%09%09%09case%20%27ddd-tel9%27%3A%0A%09%09%09%09%09usemasks%5Bk%5D%20%3D%20%27%28%23%23%29%20%23%23%23%23-%23%23%23%23%27%3B%0A%09%09%09%09%09usemasks.push%28%27%28%23%23%29%20%23%23%23%23%23-%23%23%23%23%27%29%3B%0A%09%09%09%09break%3B%0A%09%09%09%7D%0A%09%09%7D%0A%0A%09%09comp.masks%20%3D%20usemasks%3B%0A%09%09comp.masks.sort%28function%28a%2C%20b%29%7B%20return%20a.length%20-%20b.length%3B%20%7D%29%3B%0A%09%09comp.maxlengthmask%20%3D%20comp.masks%5Bcomp.masks.length-1%5D.length%3B%0A%0A%09%09objects%5Bids%5D%20%3D%20%28comp%29%3B%0A%09%09p_elem.attr%28%27data-mask-ids%27%2C%20ids%29.addClass%28%27input-masked%27%29%3B%0A%0A%09%09%24%28document%29.on%0A%09%09%28%0A%09%09%09%27keyup.simpleMask%20change.simpleMask%27%2C%0A%09%09%09%27input%5Bdata-mask-ids%3D%22%27%20+%20ids%20+%20%27%22%5D%27%2C%0A%09%09%09function%28%29%0A%09%09%09%7B%0A%09%09%09%09%24.fn.simpleMask.applyMask%28comp%29%3B%0A%09%09%09%7D%0A%09%09%29%3B%0A%09%09%0A%09%09%24%28document%29.on%0A%09%09%28%0A%09%09%09%27keydown.simpleMask%27%2C%0A%09%09%09%27input%5Bdata-mask-ids%3D%22%27%20+%20ids%20+%20%27%22%5D%27%2C%0A%09%09%09function%28e%29%0A%09%09%09%7B%0A%09%09%09%09if%20%28%21e.ctrlKey%29%0A%09%09%09%09%7B%0A%09%09%09%09%09if%20%28%20%28e.keyCode%20%3E%3D%2065%29%20%26%26%20%28e.keyCode%20%3C%3D%2090%29%20%29%0A%09%09%09%09%09%7B%0A%09%09%09%09%09%09e.preventDefault%28%29%3B%0A%09%09%09%09%09%7D%0A%09%09%09%09%7D%0A%09%09%09%7D%0A%09%09%29%3B%0A%09%09%24.fn.simpleMask.applyMask%28comp%29%3B%0A%09%7D%3B%0A%0A%7D%29%28%20jQuery%20%29%3B"));
//-->
</script>