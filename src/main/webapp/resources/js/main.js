window.addEventListener("DOMContentLoaded", () => {
    addAlertCloseListener();
    addFormSubmitListener();
    addSearchFormClearListener();
});

const addAlertCloseListener = () => {
    let categoryAlert = document.querySelector(".alert-message");
    if (categoryAlert) {
        let categoryAlertCloseBtn = categoryAlert.querySelector(".btn-close");
        categoryAlertCloseBtn.addEventListener("click", () => {
            categoryAlert.remove();
        });
        setTimeout(() => {
            categoryAlert.remove();
        }, 10000);
    }
}

const addFormSubmitListener = () => {
	let forms = document.querySelectorAll("form");
	forms.forEach(form => {
        form.addEventListener("formdata", event => {
			let formData = event.formData;
            for (let [name, value] of Array.from(formData.entries())) {
	            if (value === '') {
					formData.delete(name);
				}
	         }
        });
    });
}