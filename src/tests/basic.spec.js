import { describe, it } from 'vitest'
import { mount } from '@vue/test-utils'
import Home from '../pages/Home.vue'

describe('Home page', () => {
  it('renders feed title', () => {
    const wrapper = mount(Home)
    if (!wrapper.text().includes('Flux')) {
      throw new Error('Feed title missing')
    }
  })
})