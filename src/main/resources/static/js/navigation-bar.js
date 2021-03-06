class NavBar extends HTMLElement {

    static get observedAttributes() {
        return ['user'];
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
        if (name === 'user') {
            this.user = newValue;
        }
        this.render();
    }

    render() {
        var html = '<ul>' +
            '<li><a href="/"><img src="/logobw.png"/></a></li>' +
            '<li><a class="ffr-nav-bar-text" href="/productlist">Products</a></li>' +
            '<li><a class="ffr-nav-bar-text" href="/chains">Chains</a></li>';
        if (!this.user) {
            html += '<li class="ffr-nav-bar-right"><a class="ffr-nav-bar-text" href="/signup">Sign Up</a></li>' +
                '<li class="ffr-nav-bar-right"><a class="ffr-nav-bar-text" href="/login">Login</a></li>';
        } else {
            html += '<li><a class="ffr-nav-bar-text" href="/user/' + this.user + '/following">My Follows</a></li>' +
                '<li class="ffr-nav-bar-right"><a class="ffr-nav-bar-text" href="/logout">Sign Out</a></li>' +
                '<li class="ffr-nav-bar-right"><a class="ffr-nav-bar-text" href="/user/' + this.user + '">' + this.user + '</a></li>';
        }
        html += '<form action="/productlist">' +
            '<input class="ffr-input" style="float: left;" type="text" placeholder="Find a product..." name="name">' +
            '<button class="ffr-button" style="margin-top: 4px; float: left;" type="submit"><i class="fa fa-search"></i></button></form>';
        this.innerHTML = html + '</ul>';
    }
}
customElements.define('nav-bar', NavBar);