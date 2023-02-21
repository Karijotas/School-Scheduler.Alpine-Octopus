import React, { Component } from 'react'
import { Menu } from 'semantic-ui-react'
import logo from '../Images/techin-logo.png'
import { NavLink } from 'react-router-dom';


export default class MainMenu extends Component {


    render() {

        return (

            <Menu stackable borderless id='menu' >
                <Menu.Item
                    as={NavLink}
                    exact
                    to='/'
                    name='home'
                    content={<img alt="logo" src={logo} />}
                />
                <Menu.Item
                    
                    href='#/view/subjects'
                    name='edit'
                    content='Tvarkyti duomenis'
                />
                <Menu.Item
                    disabled
                    name='statistics'
                    content='Statistika'

                />
                <Menu.Item
                    disabled
                    // as={NavLink}
                    // exact
                    // to='/schedules'
                    name='Schedules'
                    content='Tvarkaraščiai'

                />
            </Menu>
        )
    }
}
