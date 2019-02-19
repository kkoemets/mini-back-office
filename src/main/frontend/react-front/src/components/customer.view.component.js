import React, {Component} from 'react';

import CustomerTableRow from "./tables/CustomerTableRow";

export default class CustomerView extends Component {


    constructor(props) {
        super(props);
        this.state = {customers: []};
    }

    async componentDidMount() {
        const response = await fetch('/api/customer');
        const body = await response.json();
        this.setState({customers: body, isLoading: false});
    }

    tableRow() {
        return this.state.customers.map(function(object, i) {
            return <CustomerTableRow customer={object} key={i}/>;
        });
    }

    render() {
        return (
            <div>
                <h3 align="center">Customer List</h3>
                <table className="table table-striped" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>First Name</th>
                        <th>Last name</th>
                        <th>E-mail</th>
                        <th>Phone Number</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableRow()}
                    </tbody>
                </table>
            </div>
        )
    }
}