import React, { Component } from 'react'
import { Button, Grid, Icon, Menu, Segment } from 'semantic-ui-react'
import MainMenu from '../../Components/MainMenu'
import { ObjectList } from './ViewObject'
import './EditPages/ViewGroups.css'

export default class EditMenu extends Component {
  state = { activeItem: 'subjects' }

  handleItemClick = (e, { name }) => this.setState({ activeItem: name })

  render() {
    const { activeItem } = this.state
    return (
      <div>
        
        <MainMenu />
        
        <Grid className='MainMenu '>
          
          
          <Grid.Column width={2} id='main'>            

            <Menu fluid vertical tabular className='ui centered grid'>

            

              <Menu.Item
                name='subjects'
                icon='book'
                content='Dalykai'
                active={activeItem === 'subjects'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='teachers'
                icon='user'
                content='Mokytojai'
                active={activeItem === 'teachers'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='groups'
                icon='users'
                content='Grupės'
                active={activeItem === 'groups'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='shifts'
                content='Pamainos'
                icon='sync'
                active={activeItem === 'shifts'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='rooms'
                icon='warehouse'
                content='Klasės'
                active={activeItem === 'rooms'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='program'
                icon='unordered list'
                content='Programos'
                active={activeItem === 'program'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='modules'
                content='Moduliai'
                icon='unordered list'
                active={activeItem === 'modules'}
                onClick={this.handleItemClick}
              />

            </Menu>

          </Grid.Column>

          <Grid.Column stretched textAlign='left' verticalAlign='top' width={13}>
            <Segment>


              {/* Here the selection from menu is passed to display your data table */}
            {ObjectList(activeItem)}


            </Segment>
          </Grid.Column>
        </Grid>
      </div>
    )
  }
}
