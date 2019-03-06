import React, {Component} from 'react';

import ReactTable from 'react-table';
import 'react-table/react-table.css';

import TableFilter from './table.filter.component';

export default class CustomersTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            customers: []
        };
    }

    static showAccounts(id) {
        window.location.assign("/details_customer/" + id);
    }


    static alignColumnsMiddle(columns) {
        for (let column of columns) {
            column.style = {
                textAlign: 'center'
            }
        }
    }

    render() {
        this.state.customers = this.props.customers;
        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                width: 50,
                minWidth: 50,
                maxWidth: 50
            },
            {
                Header: 'First Name',
                accessor: 'firstName',
                width: 200,
                minWidth: 200,
            },
            {
                Header: 'Last Name',
                accessor: 'lastName',
                width: 200,
                minWidth: 200,
            },
            {
                Header: 'E-mail',
                accessor: 'email',
                width: 350,
                minWidth: 350,
            },
            {
                Header: 'Phone number',
                accessor: 'phone',
                width: 200,
                minWidth: 200,
            },
            {
                Cell: props => {
                    return (
                        <button className="btn btn-primary"
                                onClick={() => {
                                    CustomersTable.showAccounts(props.row.id)
                                }}
                        >Accounts</button>
                    )
                },
                sortable: false,
                filterable: false,
            }
        ];

        CustomersTable.alignColumnsMiddle(columns);

        return (
            <div>
                <h3 align="center">Customer List</h3>
                <ReactTable
                    columns={columns}
                    data={this.state.customers}
                    filterable
                    defaultFilterMethod={TableFilter.filterMethod}
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