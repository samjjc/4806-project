class StarRating extends HTMLElement {

    static get observedAttributes() {
        return ['rating'];
    }

    constructor() {
        super();
    }

    connectedCallback() {
        if (!this.rendered) {
            this.render();
            this.rendered = true;
        }
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'rating') {
            this.rating = newValue;
        }
        this.render();
    }

    render() {
        var html = ''
        for (var i = 1; i <= 5; i++) {
            if (this.rating >= i) {
                html += '<span class="fa fa-star star"></span>';
            } else if (this.rating >= i - 0.5) {
                html += '<span class="fa fa-star-half-o star"></span>';
            } else {
                html += '<span class="fa fa-star-o star"></span>';
            }
        }
        this.innerHTML = html;
    }
}
customElements.define('star-rating', StarRating);