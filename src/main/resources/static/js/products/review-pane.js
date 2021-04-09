class ReviewPane extends HTMLElement {

    static get observedAttributes() {
        return ['user', 'rating','dos',];
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
        }else if (name === 'dos'){
            this.dos = newValue;
        }
        this.render();
    }

    render() {
        this.innerHTML = '<div class="content white-background">' +
            '<a href="/user/'+this.user+'">'+'<p>' + this.user + ' --  separation: '+this.dos+'</p>' +'</a>' +
            '<star-rating rating=' + this.rating + '></star-rating>' +
            '<p>'+ this.text +'</p>' + '</div>'
    }
}
customElements.define('review-pane', ReviewPane);