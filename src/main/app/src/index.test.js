import index from './index.js';

it('renders without crashing', () => {
    expect(JSON.stringify(index)).toMatchSnapshot();
});
