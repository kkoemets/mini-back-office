import React, {Component} from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import "../node_modules/jquery/dist/jquery.min.js";
import "../node_modules/bootstrap/dist/js/bootstrap.js";
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";

import Home from './components/home.component'
import CustomerView from './components/customer.view.component';
import TransactionCreate from './components/transaction.create.component';
import CustomerDetail from './components/customer.detail.component';
import AccountDetail from './components/account.detail.component';




class App extends Component {
    async componentDidMount() {
        const response = await fetch('/api/customer');
        const body = await response.json();
        this.setState({customers: body, isLoading: false});
    }

    render() {
        return (
            <Router>
                <div className="container">
                    <nav className="navbar navbar-expand-lg navbar-light bg-light">
                        <Link to={'/'} className="navbar-brand">Mini Back Office</Link>
                        <button className="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarSupportedContent"
                                aria-controls="navbarSupportedContent" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul className="navbar-nav mr-auto">
                                <li className="nav-item">
                                    <Link to={'/'} className="nav-link">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to={'/view_customers'} className="nav-link">
                                        View Customers</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to={'/create_transaction'} className="nav-link">
                                        Create a Transaction</Link>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <br/>
                    <Switch>
                        {/*<Route exact path='/create' component={Create}/>*/}
                        <Route path='/view_customers' component={CustomerView}/>
                        <Route path='/create_transaction' component={TransactionCreate}/>
                        <Route path={'/details_customer/:id'} component={CustomerDetail}/>
                        <Route path={'/details_account/:id'} component={AccountDetail}/>
                        <Route path='/' component={Home}/>
                    </Switch>
                    <footer className="page-footer font-small blue">
                        <div className="footer-copyright text-center py-3">Pocopay Home Assignment
                            <a href="https://www.github.com/jollerr"> Author's Github</a>
                        </div>
                    </footer>
                </div>
            </Router>
        );
    }
}

export default App;

