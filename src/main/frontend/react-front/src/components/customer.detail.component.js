import React, { Component } from 'react';
import AccountsTable from   './tables/accounts.table.component';


export default class CustomerDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            accounts: [],
            customer: ''
        };
    }

    async componentDidMount() {
        const response = await fetch('/api/account/customer/' + this.props.match.params.id);
        const body = await response.json();
        const customer = body[0].customer;
        this.state.customer = customer.firstName + ' ' + customer.lastName;
        this.setState({accounts: body, isLoading: false});
    }

    render() {
        return (
            <div>
                <h3>Account list for customer: {this.state.customer}</h3>
                <AccountsTable accounts={this.state.accounts}/>
            </div>
        )
    }
}