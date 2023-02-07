import React, { Component } from 'react'
import { Grid, Menu, Segment } from 'semantic-ui-react'
import MainMenu from '../../Components/MainMenu'
import ObjectList from './ObjectList'

export default class EditMenu extends Component {
  state = { activeItem: 'bio' }

  handleItemClick = (e, { name }) => this.setState({ activeItem: name })


  render() {
    const { activeItem } = this.state

    return (
      <div>
        <MainMenu />
        <Grid className='MainMenu'>
          <Grid.Column width={2}>
            <Menu fluid vertical tabular>
              <Menu.Item
                name='dalykai'
                content='Dalykai'
                active={activeItem === 'dalykai'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='mokytojai'
                content='Mokytojai'
                active={activeItem === 'mokytojai'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='grupės'
                content='Grupės'
                active={activeItem === 'grupės'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='pamainos'
                content='Pamainos'
                active={activeItem === 'pamainos'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='klasės'
                content='Klasės'
                active={activeItem === 'klasės'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='programos'
                content='Programos'
                active={activeItem === 'programos'}
                onClick={this.handleItemClick}
              />
              <Menu.Item
                name='moduliai'
                content='Moduliai'
                active={activeItem === 'moduliai'}
                onClick={this.handleItemClick}
              />
            </Menu>
          </Grid.Column>

          <Grid.Column stretched width={13}>
            <Segment>
              <ObjectList />
            </Segment>
          </Grid.Column>
        </Grid>
      </div>
    )
  }
}
