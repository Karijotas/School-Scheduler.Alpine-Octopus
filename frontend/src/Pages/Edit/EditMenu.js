import React, { Component } from 'react'
import { Grid, Menu, Segment } from 'semantic-ui-react'
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
                content='Dalykai'
                active={activeItem === 'subjects'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='teachers'
                content='Mokytojai'
                active={activeItem === 'teachers'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='groups'
                content='Grupės'
                active={activeItem === 'groups'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                name='shifts'
                content='Pamainos'
                active={activeItem === 'shifts'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                href='#/edit'
                name='rooms'
                content='Klasės'
                active={activeItem === 'rooms'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                href='#/edit'
                name='program'
                content='Programos'
                active={activeItem === 'program'}
                onClick={this.handleItemClick}
              />

              <Menu.Item
                href='#/edit'
                name='modules'
                content='Moduliai'
                active={activeItem === 'modules'}
                onClick={this.handleItemClick}
              />

            </Menu>

          </Grid.Column>

          <Grid.Column stretched textAlign='left' verticalAlign='bottom' width={13}>
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
