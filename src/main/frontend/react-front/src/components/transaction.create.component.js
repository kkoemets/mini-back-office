// create.component.js

import React, { Component } from 'react';
import Autosuggest from 'react-autosuggest';

// wait for fetch, just initialize array
var accounts = {
    name: 'dummy',
    year: 0
};

// https://developer.mozilla.org/en/docs/Web/JavaScript/Guide/Regular_Expressions#Using_Special_Characters
function escapeRegexCharacters(str) {
    return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

// Teach Autosuggest how to calculate suggestions for any given input value.
function getSuggestions(value) {
    const escapedValue = escapeRegexCharacters(value.trim());
    const regex = new RegExp('^' + escapedValue, 'i');
    return accounts.filter(account => regex.test(account.name) || regex.test(account.balance));
}

function getSuggestionName(suggestion) {
    return suggestion.name;
}

function renderSuggestion(suggestion) {
    return (
        <span>{suggestion.name} - BALANCE {suggestion.balance}</span>
    );
}

export default class TransactionCreate extends Component {
    constructor() {
        super();
        this.onChangeSenderAccountId = this.onChangeSenderAccountId.bind(this);
        this.onChangeReceiverAccountId = this.onChangeReceiverAccountId.bind(this);
        this.onChangeAmount = this.onChangeAmount.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            nameSuggestions: [],
            senderAccountName: '',
            receiverAccountName: '',
            amount:'',
            description:''
        }
    }

    async componentDidMount() {
        const response = await fetch('/api/account');
        const body = await response.json();
        accounts = await body.filter(account => (account.name));
        await console.log(accounts)
    }

    onNameChange = (event, { newValue }) => {
        this.setState({
            senderAccountName: newValue
        });
    };

    onReceiverAccountNameChange = (event, { newValue }) => {
        this.setState({
            receiverAccountName: newValue
        });
    };

    // Autosuggest will call this function every time you need to update suggestions.
    // You already implemented this logic above, so just use it.
    onNicknameSuggestionsFetchRequested = ({ value }) => {
        this.setState({
            nameSuggestions: getSuggestions(value)
        });
    };

    // Autosuggest will call this function every time you need to clear suggestions.
    onNicknameSuggestionsClearRequested = () => {
        this.setState({
            nameSuggestions: []
        });
    };

    onChangeSenderAccountId(e) {
        this.setState({
            senderAccountName: e.target.value
        });
    }

    onChangeReceiverAccountId(e) {
        this.setState({
            senderAccountName: e.target.value
        });
    }

    onChangeAmount(e) {
        this.setState({
            amount: e.target.value
        })
    }

    onChangeDescription(e) {
        this.setState({
            description: e.target.value
        })
    }

    onSubmit(e) {
        e.preventDefault();
        var obj = {
            senderAccountName: this.state.senderAccountName,
            receiverAccountName: this.state.receiverAccountName,
            amount: this.state.amount,
            description: this.state.description
        };

        console.log(obj);

        fetch('http://localhost:8080/api/transaction/create', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(obj)
        });

        this.setState({
            senderAccountName: '',
            receiverAccountName: '',
            amount: '',
            description: ''
        })
    }

    render() {
        const {
            senderAccountName,
            receiverAccountName,
            nameSuggestions,
        } = this.state;
        // Autosuggest will pass through all these props to the input.
        const nameInputProps = {
            placeholder: "auto-suggest enabled",
            value: senderAccountName,
            onChange: this.onNameChange,
            required: true
        };

        const receiverAccountNameInputProps = {
            placeholder: "auto-suggest enabled",
            value: receiverAccountName,
            onChange: this.onReceiverAccountNameChange,
            required: true
        };

        return (
            <div style={{ marginTop: 10 }}>
                <h3>Create a new Transaction</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Sender Account Name: </label>
                        <Autosuggest
                            suggestions={nameSuggestions}
                            onSuggestionsFetchRequested={this.onNicknameSuggestionsFetchRequested}
                            onSuggestionsClearRequested={this.onNicknameSuggestionsClearRequested}
                            getSuggestionValue={getSuggestionName}
                            renderSuggestion={renderSuggestion}
                            inputProps={nameInputProps}
                        />
                    </div>
                    <div className="form-group">
                        <label>Receiver Account Name: </label>
                        <Autosuggest
                            suggestions={nameSuggestions}
                            onSuggestionsFetchRequested={this.onNicknameSuggestionsFetchRequested}
                            onSuggestionsClearRequested={this.onNicknameSuggestionsClearRequested}
                            getSuggestionValue={getSuggestionName}
                            renderSuggestion={renderSuggestion}
                            inputProps={receiverAccountNameInputProps}
                        />
                    </div>

                    <div className="form-group">
                        <label>Amount: </label>
                        <input type="number"
                               className="form-check"
                               min="0"
                               value={this.state.amount}
                               onChange={this.onChangeAmount}
                               required
                        />
                    </div>

                    <div className="form-group">
                        <label>Description: </label>
                        <br/>
                        <textarea
                            rows="3"
                            cols="50"
                            placeholder="Write a short description..."
                            value={this.state.description}
                            onChange={this.onChangeDescription}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Create a Transaction"
                               className="btn btn-primary"/>
                    </div>
                </form>
            </div>
        )
    }
}