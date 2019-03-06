import React, {Component} from 'react';

import CustomersTable from './tables/customers.table.component';


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

    render() {
        return (
            <div>
                <CustomersTable customers={this.state.customers}/>
            </div>
        )
    }
}