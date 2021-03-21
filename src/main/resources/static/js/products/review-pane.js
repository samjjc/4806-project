class ReviewPane extends HTMLElement {

    static get observedAttributes() {
        return ['user', 'rating',];
    }

    constructor() {
        super();
        this.text = this.textContent;
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
        }else if (name === 'user') {
            this.user = newValue;
        }
        this.render();
    }

    render() {
        this.innerHTML = '<div class="content white-background">' +
            '<p>' + this.user + '</p>' +
            '<star-rating rating=' + this.rating + '></star-rating>' +
            '<p>'+ this.text +'</p>' + '</div>'
    }
}
customElements.define('review-pane', ReviewPane);