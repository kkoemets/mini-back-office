import React, {Component} from 'react';

import ReactTable from 'react-table';
import 'react-table/react-table.css';

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

    showAccounts(id) {
        window.location.assign("/details_customer/" + id);
    }

    filterMethod = (filter, row, column) => {
        const id = filter.pivotId || filter.id;
        return row[id] !== undefined ? String(row[id].toLowerCase()).startsWith(filter.value.toLowerCase()) : true
    }

    render() {
        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                style: {
                    textAlign: 'center'
                },
                width : 50,
                minWidth : 50,
                maxWidth : 50
            },
            {
                Header: 'First Name',
                accessor: 'firstName',
                style: {
                    textAlign: 'center'
                },
                width : 200,
                minWidth : 200,
            },
            {
                Header: 'Last Name',
                accessor: 'lastName',
                style: {
                    textAlign: 'center'
                },
                width : 200,
                minWidth : 200,
            },
            {
                Header: 'E-mail',
                accessor: 'email',
                style: {
                    textAlign: 'center'
                },
                width : 350,
                minWidth : 350,
            },
            {
                Header: 'Phone number',
                accessor: 'phone',
                style: {
                    textAlign: 'center'
                },
                width : 200,
                minWidth : 200,
            },
            {
                Cell: props => {
                    return (
                        <button className="btn btn-primary"
                        onClick={ () => {
                            this.showAccounts(props.row.id)
                        }}
                        >Accounts</button>
                    )
                },
                style: {
                    textAlign: 'center'
                },
                sortable: false,
                filterable: false,
            }
        ]

        return (
            <div>
                <h3 align="center">Customer List</h3>
                <ReactTable
                    columns={columns}
                    data={this.state.customers}
                    filterable
                    defaultFilterMethod={this.filterMethod}
                    defaultPageSize={20}
                    noDataText={'Loading customers, please wait...'}
                    showPaginationTop
                    showPaginationBottom={false}
                    className="-striped -highlight"
                />
            </div>
        )
    }
}