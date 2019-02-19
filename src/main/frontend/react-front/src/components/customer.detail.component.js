import React, { Component } from 'react';
import AccountTableRow from "./tables/AccountTableRow";

export default class CustomerDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            accounts: []
        };
    }

    async componentDidMount() {
        console.log(this.props.match.params.id);
        const response = await fetch('/api/account/customer/' + this.props.match.params.id);
        const body = await response.json();
        this.setState({accounts: body, isLoading: false});
    }

    tableRow() {
        return this.state.accounts.map(function(object, i) {
            return <AccountTableRow account={object} key={i}/>;
        });
    }

    render() {
        return (
            <div>
                <h3 align="center">Account List</h3>
                <table className="table table-striped" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Username</th>
                        <th>Balance, eur</th>
                    </tr>
                    </thead>
                    <tbody>
                    {console.log(this.state.accounts)}
                    {this.tableRow()}
                    </tbody>
                </table>
            </div>
        )
    }
}