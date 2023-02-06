import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Menu } from 'semantic-ui-react'
import logo from '../Images/techin-logo.png'
export default class MenuExampleTabular extends Component {
    state = { activeItem: 'bio' }

    handleItemClick = (e, { name }) => this.setState({ activeItem: name })

    render() {
        const { activeItem } = this.state

        return (
            <Menu stackable >
                <Menu.Item>
                    <img alt="logo" src={logo} />
                </Menu.Item>   <Menu.Item
                    name='edit'
                    content='Tvarkyti duomenis'
                    active={activeItem === 'edit'}
                    onClick={this.handleItemClick}
                />
                {/* <MenuExampleTabularOnRight/> */}
                <Menu.Item
                    name='statistics'
                    content='Statistika'
                    active={activeItem === 'statistics'}
                    onClick={this.handleItemClick}
                />
                <Menu.Item
                    name='Schedules'
                    content='Tvarkaraščiai'
                    active={activeItem === 'Schedules'}
                    onClick={this.handleItemClick}
                />
            </Menu>
        )
    }
}
